package za.ac.university.pretoria.node.mvc.controller;

import org.apache.log4j.Logger;
import za.ac.university.pretoria.node.api.NodeHandler;
import za.ac.university.pretoria.node.api.NodeManager;
import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
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
    public void setNodeHandler(NodeHandler nodeHandler) {
        this.nodeHandler = nodeHandler;
    }

    public NodeManagerImpl() throws NamingException {
        executorService = Executors.newCachedThreadPool();
    }

    @PostConstruct
    public void init() {
        try {
            hydrateNodes();
            startTimer();
        } catch (SQLException | NodeException e) {
            logger.error("There was a problem initiating the node manager");
        }
        startTimer();
    }

    @Override
    public boolean hydrateNodes() throws SQLException, NodeException {

        logger.info("NodeManager: Hydrating all active nodes and changing unavailable nodes to active according to node calendar");
        checkActiveNodes();
        checkUnavailableNodes();

        return false;
    }

    @Override
    public boolean createNode(NodeInfo nodeInfo) throws SQLException {
        logger.info("NodeManager: Creating node " + nodeInfo);
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
                logger.info("NodeManager: Removing node " + node);
                nodes.remove(node);
            }
        }
        return true;
    }

    @Override
    public boolean killNode(NodeInfo nodeInfo) {

        logger.info("NodeManager: killing node" + nodeInfo.getNodeId());
        Future future = nodes.get(nodeInfo.getNodeId());
        if(future != null) {
            future.cancel(true);
            nodes.remove(nodeInfo.getNodeId());
        }
        return true;
    }

    @Override
    public void checkUnavailableNodes() throws SQLException, NodeException {
        logger.info("NodeManager: changing unvailable nodes to active according to node calendar");
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
        logger.info("NodeManager: Creating active nodes and reseting busy nodes");
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
                    logger.info("NodeManager: Executing timer service");
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
        int activeNodes = nodeHandler.getTotalActiveNodes();
        logger.info("NodeManager: Total active nodes " + activeNodes);
        return activeNodes;
    }

    @Override
    public Integer getUnavailableNodes() throws SQLException {
        int unavailableNodes = nodeHandler.getTotalUnavailableNodes();
        logger.info("NodeManager: Total unavailable nodes " + unavailableNodes);
        return unavailableNodes;
    }

    @Override
    public Integer getBusyNodes() throws SQLException {
        int busyNodes = nodeHandler.getTotalBusyNodes();
        logger.info("NodeManager: Total busy nodes " + busyNodes);
        return busyNodes;
    }

    @Override
    public Integer getTotalNodes() throws SQLException {
        int totalNodes = nodeHandler.getTotalBusyNodes();
        logger.info("NodeManager: Total nodes " + totalNodes);
        return totalNodes;
    }
}
