package za.ac.university.pretoria.node.api;

import za.ac.university.pretoria.node.mvc.model.NodeInfo;

public interface NodeManager {

    public boolean hydrateNodes();

    public boolean createNode(NodeInfo nodeInfo);

    public boolean clearAllFinishedNodes();

    public boolean killNode(NodeInfo nodeInfo);

    public void checkUnavailableNodes();

    public void checkActiveNodes();

    public Integer getActiveNodes();

    public Integer getUnavailableNodes();

    public Integer getBusyNodes();

    public Integer getTotalNodes();
}
