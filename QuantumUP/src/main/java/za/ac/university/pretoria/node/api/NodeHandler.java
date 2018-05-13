package za.ac.university.pretoria.node.api;

import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import java.util.List;

public interface NodeHandler {

    public boolean isNodeActive(String nodeID);
    public boolean setNodeActive(String nodeID);
    public boolean setNodeUnavailable(String nodeID);
    public List<NodeInfo> getUnavailableNodes();
    public List<NodeInfo> getAvailableNodes();


}
