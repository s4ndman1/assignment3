package za.ac.university.pretoria.node.mvc;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;

import org.junit.Test;
import za.ac.university.pretoria.node.mvc.controller.DatabaseConnection;
import za.ac.university.pretoria.node.api.NodeUI;
import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;
import za.ac.university.pretoria.node.mvc.view.NodeUIImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class nodeManagerTest {


    private DatabaseConnection connection;
    private NodeUI manager;

    @Before
    public void init() throws SQLException, ClassNotFoundException {
        connection =mock(DatabaseConnection.class);
    }

    @Test
    public void testAddNode() throws SQLException, NodeException {
        LocalTime startTime = LocalTime.of(6, 0, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0, 0);
        String nodeID = manager.approveNode("ADN2018051304223", startTime, endTime);
        String query = "SELECT * FROM NODE_INFO WHERE NODE_ID = '" + nodeID + "'";
        ResultSet resultSet = connection.executeQuery(query);
        List<String> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(resultSet.getString(1));
        }
        for (String result : results) {
            assertTrue(nodeID.equalsIgnoreCase(result));
        }

    }


    @Test
    public void testDeleteNode() throws SQLException, NodeException {
        LocalTime startTime = LocalTime.of(6, 0, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0, 0);
        String nodeID = manager.approveNode("ADN2018051304223", startTime, endTime);

        manager.removeNode(nodeID);

        String query = "SELECT * FROM NODE_INFO WHERE NODE_ID = '" + nodeID + "'";
        ResultSet resultSet = connection.executeQuery(query);
        assertFalse(resultSet.next());

    }


    @Test
    public void testKillNode() throws SQLException, NodeException {
        LocalTime startTime = LocalTime.of(6, 0, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0, 0);
        String nodeID = manager.approveNode("ADN2018051304223", startTime, endTime);

        manager.killNode(nodeID);

        String query = "SELECT * FROM NODE_INFO WHERE NODE_ID = '" + nodeID + "'";
        ResultSet resultSet = connection.executeQuery(query);

        List<String> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(resultSet.getString(4));
        }
        for (String result : results) {
            assertTrue("3".equalsIgnoreCase(result));
        }

    }

    @Test
    public void testGetAllNodes() throws SQLException, NodeException {

        LocalTime startTime = LocalTime.of(6, 0, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0, 0);
        String nodeID = manager.approveNode("ADN2018051304223", startTime, endTime);

        List<NodeInfo> nodeInfos = manager.viewAllNodes();
        assertTrue(nodeInfos.size() > 0);
    }

    @Test
    public void testGetOwnNodes() throws SQLException, NodeException {

        LocalTime startTime = LocalTime.of(6, 0, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0, 0);
        String nodeID = manager.approveNode("ADN2018051304223", startTime, endTime);

        List<NodeInfo> nodeInfos = manager.viewAllOwnNodes("ADN2018051304223");
        assertTrue(nodeInfos.size() > 0);
    }


    @Test
    public void testSetActiveNode() throws SQLException, NodeException {

        LocalTime startTime = LocalTime.of(6, 0, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0, 0);
        String nodeID = manager.approveNode("ADN2018051304223", startTime, endTime);

        manager.changeNodeToActive(nodeID);

        String query = "SELECT * FROM NODE_INFO WHERE NODE_ID = '" + nodeID + "'";
        ResultSet resultSet = connection.executeQuery(query);

        List<String> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(resultSet.getString(4));
        }
        for (String result : results) {
            assertTrue("1".equalsIgnoreCase(result));
        }
    }
}
