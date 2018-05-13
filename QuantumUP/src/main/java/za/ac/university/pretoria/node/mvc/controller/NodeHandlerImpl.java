package za.ac.university.pretoria.node.mvc.controller;

import za.ac.university.pretoria.node.api.NodeHandler;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import java.util.List;

public class NodeHandlerImpl implements NodeHandler {
    @Override
    public boolean isNodeActive(String nodeID) {
        return false;
    }

    @Override
    public boolean setNodeActive(String nodeID) {
        return false;
    }

    @Override
    public boolean setNodeUnavailable(String nodeID) {
        return false;
    }

    @Override
    public List<NodeInfo> getUnavailableNodes() {
        return null;
    }

    @Override
    public List<NodeInfo> getAvailableNodes() {
        return null;
    }
}
