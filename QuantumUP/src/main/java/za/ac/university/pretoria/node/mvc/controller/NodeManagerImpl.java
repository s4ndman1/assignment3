package za.ac.university.pretoria.node.mvc.controller;

import org.apache.log4j.Logger;
import za.ac.university.pretoria.node.api.NodeHandler;
import za.ac.university.pretoria.node.api.NodeManager;
import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Singleton
@Startup
public class NodeManagerImpl implements NodeManager {

    private ExecutorService executorService;
    private NodeHandler nodeHandler;
    Map<String, Future> nodes;
    private Logger logger = Logger.getLogger(NodeManagerImpl.class);
    InitialContext ctx;

    @Inject
    public NodeManagerImpl(NodeHandlerImpl nodeHandler) throws SQLException, NodeException, NamingException {
        ctx = new InitialContext();
        this.nodeHandler = nodeHandler;
        executorService = Executors.newCachedThreadPool();
        hydrateNodes();
        startTimer();
    }

    @Override
    public boolean hydrateNodes() throws SQLException, NodeException {

        checkActiveNodes();
        checkUnavailableNodes();

        return false;
    }

    @Override
    public boolean createNode(NodeInfo nodeInfo) throws SQLException {

        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    NodeImpl node = (NodeImpl) ctx.lookup("node");
                    node.setNodeId(nodeInfo.getNodeId());
                } catch (NamingException e) {
                    logger.error("There was a problem starting up the node ", e);
                }
            }
        });
        nodeHandler.setNodeActive(nodeInfo.getNodeId());
        nodes.put(nodeInfo.getNodeId(), future);
        return true;
    }

    @Override
    public boolean clearAllFinishedNodes() {

        for (String node : nodes.keySet()) {
            Future future = nodes.get(node);
            if (future.isDone()){
                nodes.remove(node);
            }
        }
        return true;
    }

    @Override
    public boolean killNode(NodeInfo nodeInfo) {
        Future future = nodes.get(nodeInfo.getNodeId());
        future.cancel(true);
        nodes.remove(nodeInfo.getNodeId());
        return true;
    }

    @Override
    public synchronized void checkUnavailableNodes() throws SQLException, NodeException {
        List<NodeInfo> nodes = nodeHandler.getUnavailableNodes();
        for (NodeInfo node : nodes) {
            if (nodeHandler.isNodeActive(node.getNodeId())) {
                createNode(node);
            }
        }
    }

    @Override
    public void checkActiveNodes() throws SQLException {
        List<NodeInfo> nodes = nodeHandler.getAvailableNodes();
        for (NodeInfo node : nodes) {
            if (node.getState().equalsIgnoreCase("Active")) {
                createNode(node);
            }
            else if (node.getState().equalsIgnoreCase("Busy"))
                nodeHandler.setInterruptedNodeActive(node.getNodeId());
                createNode(node);
        }
    }

    public void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    checkUnavailableNodes();
                } catch (SQLException | NodeException e) {
                   logger.error("There was an problem checking unavailable nodes ", e);
                }
                clearAllFinishedNodes();
            }
        }, 60000);
    }

    @Override
    public Integer getActiveNodes() throws SQLException {
        return nodeHandler.getTotalActiveNodes();
    }

    @Override
    public Integer getUnavailableNodes() throws SQLException {
        return nodeHandler.getTotalUnavailableNodes();
    }

    @Override
    public Integer getBusyNodes() throws SQLException {
        return nodeHandler.getTotalBusyNodes();
    }

    @Override
    public Integer getTotalNodes() throws SQLException {
        return nodeHandler.getTotalNodeCount();
    }
}
