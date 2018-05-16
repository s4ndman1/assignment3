package za.ac.university.pretoria.node.api;

import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import java.sql.SQLException;

public interface NodeManager {

    public boolean hydrateNodes() throws SQLException, NodeException;

    public boolean createNode(NodeInfo nodeInfo) throws SQLException;

    public boolean clearAllFinishedNodes();

    public boolean killNode(NodeInfo nodeInfo);

    public void checkUnavailableNodes() throws SQLException, NodeException;

    public void checkActiveNodes() throws SQLException, NodeException;

    public Integer getActiveNodes() throws SQLException;

    public Integer getUnavailableNodes() throws SQLException;

    public Integer getBusyNodes() throws SQLException;

    public Integer getTotalNodes() throws SQLException;
}
