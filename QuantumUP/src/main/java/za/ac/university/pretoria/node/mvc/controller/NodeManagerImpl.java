package za.ac.university.pretoria.node.mvc.controller;

import za.ac.university.pretoria.node.api.NodeHandler;
import za.ac.university.pretoria.node.api.NodeManager;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NodeManagerImpl implements NodeManager {

    private ExecutorService executorService;
    private NodeHandler nodeHandler;
    Map<String, Future> nodes;
    private Integer activeNodes;
    private Integer unavailableNodes;
    private Integer busyNodes;
    private Integer totalNodes;

    public NodeManagerImpl() {
        nodeHandler = new NodeHandlerImpl();
        executorService = Executors.newCachedThreadPool();
        hydrateNodes();
        startTimer();
    }

    @Override
    public boolean hydrateNodes() {

        checkActiveNodes();
        checkUnavailableNodes();

        return false;
    }

    @Override
    public boolean createNode(NodeInfo nodeInfo) {

        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    new NodeImpl();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

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
    public void checkUnavailableNodes() {
        List<NodeInfo> nodes = nodeHandler.getUnavailableNodes();
        for (NodeInfo node : nodes) {
            if (nodeHandler.isNodeActive(node.getNodeId())) {
                createNode(node);
                activeNodes++;
                unavailableNodes--;
            }
        }
    }

    @Override
    public void checkActiveNodes() {
        List<NodeInfo> nodes = nodeHandler.getAvailableNodes();
        for (NodeInfo node : nodes) {
            if (nodeHandler.isNodeActive(node.getNodeId())) {
                createNode(node);
                activeNodes++;
                unavailableNodes--;
            }
        }
    }

    public void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkUnavailableNodes();
                clearAllFinishedNodes();
            }
        }, 60000);
    }

    @Override
    public Integer getActiveNodes() {
        return activeNodes;
    }

    @Override
    public Integer getUnavailableNodes() {
        return unavailableNodes;
    }

    @Override
    public Integer getBusyNodes() {
        return busyNodes;
    }

    @Override
    public Integer getTotalNodes() {
        return totalNodes;
    }
}
