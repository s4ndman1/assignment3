package za.ac.university.pretoria.node.api;

import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;
import za.ac.university.pretoria.node.mvc.model.Task.Task;

import java.sql.SQLException;
import java.util.List;

public interface NodeHandler {

    public boolean isNodeAlreadyActive(String nodeID) throws SQLException;

    public boolean isNodeActive(String nodeID) throws SQLException, NodeException;

    public boolean setNodeActive(String nodeID) throws SQLException;

    public boolean setNodeUnavailable(String nodeID) throws SQLException;

    public boolean setNodeBusy(String nodeID) throws SQLException;

    public boolean setInterruptedNodeActive(String nodeID) throws SQLException;

    public List<NodeInfo> getUnavailableNodes() throws SQLException;

    public List<NodeInfo> getAvailableNodes() throws SQLException;

    public int getTotalNodeCount() throws SQLException;

    public int getTotalActiveNodes() throws SQLException;

    public int getTotalBusyNodes() throws SQLException;

    public int getTotalUnavailableNodes() throws SQLException;

    public boolean addTask(Task task, String nodeID) throws SQLException;

    public boolean updateTask(Task task, String nodeID) throws SQLException;

}
