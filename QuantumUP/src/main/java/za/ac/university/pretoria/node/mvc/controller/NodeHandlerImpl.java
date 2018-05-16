package za.ac.university.pretoria.node.mvc.controller;

import za.ac.university.pretoria.node.api.NodeHandler;
import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class NodeHandlerImpl implements NodeHandler {

    private DatabaseConnection connection;

    @Inject
    public NodeHandlerImpl(DatabaseConnection connection) throws SQLException, ClassNotFoundException {
        this.connection = connection;
    }

    @Override
    public boolean isNodeActive(String nodeID) throws SQLException, NodeException {

        String query = "SELECT node_active_start_time, node_active_end_time FROM NODE_CALENDAR WHERE calender_id = '" + nodeID + "'";

        ResultSet resultSet = connection.executeQuery(query);
        List<NodeInfo> nodeInfos = new ArrayList<>();
        LocalTime activeTime = null;
        LocalTime endTime = null;
        while (resultSet.next()) {

            activeTime = LocalTime.parse(resultSet.getString(1));
            endTime = LocalTime.parse(resultSet.getString(2));

        }
        if (activeTime == null)
            throw new NodeException("The active time was not set for node " + nodeID);
        if (endTime == null)
            throw new NodeException("The end time was not set for node " + nodeID);
        LocalTime currentTime = LocalTime.now();

        if (currentTime.compareTo(activeTime) >= 0 && currentTime.compareTo(endTime) < 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean setNodeActive(String nodeID) throws SQLException {

        String query = "UPDATE NODE_INFO "
                + "SET NODE_STATUS = "
                + "(SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Active')"
                + " where NODE_ID = '" + nodeID + "'";

        connection.executeQuery(query);
        return true;
    }

    @Override
    public boolean setNodeUnavailable(String nodeID) throws SQLException {
        String query = "UPDATE NODE_INFO "
                + "SET NODE_STATUS = "
                + "(SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Active')"
                + " where NODE_ID = '" + nodeID + "'";

        connection.executeQuery(query);
        return true;
    }

    @Override
    public boolean setNodeBusy(String nodeID) throws SQLException {
        String query = "UPDATE NODE_INFO "
                + "SET NODE_STATUS = "
                + "(SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Busy')"
                + " where NODE_ID = '" + nodeID + "'";

        connection.executeQuery(query);
        return true;
    }

    @Override
    public boolean setInterruptedNodeActive(String nodeID) throws SQLException {
        String query = "DELETE FROM NODE_TASKS WHERE  NODE_ID_FK = '"+nodeID+"' AND END_TIME IS NULL";
        connection.executeQuery(query);

        query = "UPDATE NODE_INFO "
                + "SET NODE_STATUS = "
                + "(SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Active')"
                + " where NODE_ID = '" + nodeID + "'";

        connection.executeQuery(query);
        return true;
    }

    @Override
    public List<NodeInfo> getUnavailableNodes() throws SQLException {

        String query = "SELECT NODE_ID FROM NODE_INFO WHERE NODE_STATUS = (SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Unavailable')";

        ResultSet resultSet = connection.executeQuery(query);
        List<NodeInfo> nodeInfos = new ArrayList<>();

        while (resultSet.next()) {
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setNodeId(resultSet.getString(1));
            nodeInfos.add(nodeInfo);
        }
        return nodeInfos;
    }

    @Override
    public List<NodeInfo> getAvailableNodes() throws SQLException {

        String query = "SELECT NODE_ID FROM NODE_INFO WHERE NODE_STATUS = (SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Unavailable')";

        ResultSet resultSet = connection.executeQuery(query);
        List<NodeInfo> nodeInfos = new ArrayList<>();

        while (resultSet.next()) {
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setNodeId(resultSet.getString(1));
            nodeInfos.add(nodeInfo);
            nodeInfo.setState("Active");
            nodeInfos.add(nodeInfo);
        }

        query = "SELECT NODE_ID FROM NODE_INFO WHERE NODE_STATUS = (SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Busy')";

        resultSet = connection.executeQuery(query);

        while (resultSet.next()) {
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setNodeId(resultSet.getString(1));
            nodeInfos.add(nodeInfo);
            nodeInfo.setState("Busy");
            nodeInfos.add(nodeInfo);
        }

        return nodeInfos;
    }

    @Override
    public int getTotalNodeCount() throws SQLException {


        String query = "SELECT NODE_ID FROM NODE_INFO";

        ResultSet resultSet = connection.executeQuery(query);

        int totalResults = resultSet.getFetchSize();

        return totalResults;
    }

    @Override
    public int getTotalActiveNodes() throws SQLException {
        String query = "SELECT NODE_ID FROM NODE_INFO WHERE NODE_STATUS = (SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Active')";

        ResultSet resultSet = connection.executeQuery(query);

        int totalResults = resultSet.getFetchSize();

        return totalResults;
    }

    @Override
    public int getTotalBusyNodes() throws SQLException {
        String query = "SELECT NODE_ID FROM NODE_INFO WHERE NODE_STATUS = (SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Busy')";

        ResultSet resultSet = connection.executeQuery(query);

        int totalResults = resultSet.getFetchSize();

        return totalResults;
    }

    @Override
    public int getTotalUnavailableNodes() throws SQLException {
        String query = "SELECT NODE_ID FROM NODE_INFO WHERE NODE_STATUS = (SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Unavailable')";

        ResultSet resultSet = connection.executeQuery(query);

        int totalResults = resultSet.getFetchSize();

        return totalResults;
    }
}
