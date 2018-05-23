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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Singleton
@Startup
public class NodeManagerImpl implements NodeManager {

    private ExecutorService executorService;
    private NodeHandler nodeHandler;
    Map<String, Future> nodesFuture;
    private Logger logger = Logger.getLogger(NodeManagerImpl.class);
    private Context ctx;

    @Inject
    public void setNodeHandler(NodeHandler nodeHandler) {
        this.nodeHandler = nodeHandler;
    }

    public NodeManagerImpl() throws NamingException {
        this.nodesFuture = new HashMap<>();
        ctx = new InitialContext();
        executorService = Executors.newCachedThreadPool();
    }

    @PostConstruct
    public void init() {
        try {
            hydrateNodes();
            startTimer();
        } catch (SQLException | NodeException e) {
            logger.error("There was a problem initiating the node manager", e);
        }
    }

    @Override
    public boolean hydrateNodes() throws SQLException, NodeException {

        logger.info("NodeManager: Hydrating all active nodesFuture and changing unavailable nodesFuture to active according to node calendar");
        checkActiveNodes();
        checkUnavailableNodes();

        return false;
    }

    @Override
    public boolean createNode(NodeInfo nodeInfo) throws SQLException {
        logger.info("NodeManager: Creating node " + nodeInfo.getNodeId());
        Future future = executorService.submit(() -> {
            try {
                NodeImpl node = (NodeImpl) ctx.lookup("java:global.QuantumUP-1.0.0-SNAPSHOT.NodeImpl");
                return node.startOperations(nodeInfo.getNodeId());
            } catch (NamingException e) {
                logger.error("There was a problem starting up the node ", e);
                throw e;
            }
        });
        nodeHandler.setNodeActive(nodeInfo.getNodeId());
        nodesFuture.put(nodeInfo.getNodeId(), future);
        return true;
    }

    @Override
    public boolean clearAllFinishedNodes() throws SQLException {

        for (String node : nodesFuture.keySet()) {
            Future future = nodesFuture.get(node);
            if (future.isDone()) {
                logger.info("NodeManager: Removing node " + node);
                nodesFuture.remove(node);
                nodeHandler.setNodeUnavailable(node);
            }
        }
        return true;
    }

    @Override
    public boolean killNode(NodeInfo nodeInfo) throws SQLException {

        logger.info("NodeManager: killing node" + nodeInfo.getNodeId());
        Future future = nodesFuture.get(nodeInfo.getNodeId());
        if (future != null) {
            future.cancel(true);
            nodesFuture.remove(nodeInfo.getNodeId());
            nodeHandler.setNodeUnavailable(nodeInfo.getNodeId());
        }
        return true;
    }

    @Override
    public void checkUnavailableNodes() throws SQLException, NodeException {
        logger.info("NodeManager: changing unvailable nodesFuture to active according to node calendar");
        List<NodeInfo> nodes = nodeHandler.getUnavailableNodes();
        for (NodeInfo node : nodes) {
            if (nodeHandler.isNodeActive(node.getNodeId())) {
                if (nodesFuture.get(node.getNodeId()) == null)
                    createNode(node);
            }
        }
    }

    @Override
    public void checkActiveNodes() throws SQLException {
        List<NodeInfo> nodes = nodeHandler.getAvailableNodes();
        logger.info("NodeManager: Creating active nodesFuture and reseting busy nodesFuture");
        for (NodeInfo node : nodes) {
            if (node.getState().equalsIgnoreCase("Active")) {
                if (nodesFuture.get(node.getNodeId()) == null)
                    createNode(node);
            } else if (node.getState().equalsIgnoreCase("Busy"))
                if (nodesFuture.get(node.getNodeId()) == null) {
                    nodeHandler.setInterruptedNodeActive(node.getNodeId());
                    createNode(node);
                }
        }
    }

    public void startTimer() {
        Timer timer = new Timer();

        TimerTask checkNodes = new TimerTask() {
            @Override
            public void run() {
                try {
                    clearAllFinishedNodes();
                    logger.info("NodeManager: Executing timer service");
                    checkUnavailableNodes();
                } catch (SQLException | NodeException e) {
                    logger.error("There was an problem checking unavailable nodesFuture ", e);
                }
                logger.info("NodeManager: Garbage collecting all finished nodesFuture");
            }
        };

        timer.scheduleAtFixedRate(checkNodes, new Date(), 60000l);
    }

    @Override
    public Integer getActiveNodes() throws SQLException {
        int activeNodes = nodeHandler.getTotalActiveNodes();
        logger.info("NodeManager: Total active nodesFuture " + activeNodes);
        return activeNodes;
    }

    @Override
    public Integer getUnavailableNodes() throws SQLException {
        int unavailableNodes = nodeHandler.getTotalUnavailableNodes();
        logger.info("NodeManager: Total unavailable nodesFuture " + unavailableNodes);
        return unavailableNodes;
    }

    @Override
    public Integer getBusyNodes() throws SQLException {
        int busyNodes = nodeHandler.getTotalBusyNodes();
        logger.info("NodeManager: Total busy nodesFuture " + busyNodes);
        return busyNodes;
    }

    @Override
    public Integer getTotalNodes() throws SQLException {
        int totalNodes = nodeHandler.getTotalBusyNodes();
        logger.info("NodeManager: Total nodesFuture " + totalNodes);
        return totalNodes;
    }
}
