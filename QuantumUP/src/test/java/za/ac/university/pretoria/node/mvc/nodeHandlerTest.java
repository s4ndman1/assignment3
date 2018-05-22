/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.university.pretoria.node.mvc;

import java.sql.ResultSet;
import za.ac.university.pretoria.node.api.NodeHandler;
import za.ac.university.pretoria.node.api.NodeManager;
import za.ac.university.pretoria.node.mvc.controller.DatabaseConnection;
import za.ac.university.pretoria.node.mvc.controller.NodeImpl;
import za.ac.university.pretoria.node.mvc.model.Report.FinalReport;
import za.ac.university.pretoria.node.mvc.model.Report.Report;
import za.ac.university.pretoria.node.mvc.model.Report.Value;
import za.ac.university.pretoria.node.mvc.model.Report.ValueMetaData;
import org.junit.Before;
import org.junit.Test;
import za.ac.university.pretoria.node.mvc.model.Task.Measurement;
import za.ac.university.pretoria.node.mvc.model.Task.Task;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author mm185494
 */
public class nodeHandlerTest {
    
    NodeImpl nodeImpl;
    NodeHandler nodeHandler;
    NodeManager manager;
    private DatabaseConnection connection;
    
     @Before
    public void init() throws SQLException, ClassNotFoundException {
        connection =mock(DatabaseConnection.class);
    }
    
//    @Test
//    public void testAddTask() throws SQLException {
//        LocalTime startTime = LocalTime.of(6, 0, 0, 0);
//        LocalTime endTime = LocalTime.of(12, 0, 0, 0);
//        String nodeID = manager.approveNode("ADN2018051304223", startTime, endTime);
//        //boolean taskadd = nodeHandler.addTask(new Task(),nodeID);
//        String query = "SELECT * FROM NODE_TASKS WHERE NODE_ID = '" + nodeID + "'";
//        ResultSet resultSet = connection.executeQuery(query);
//        List<String> results = new ArrayList<>();
//        while (resultSet.next()) {
//            results.add(resultSet.getString(1));
//        }
//        for (String result : results) {
//            assertTrue(nodeHandler.addTask(new Task(),result));
//        }
//
//    }
//
//    @Test
//    public void testisNodeActive() throws SQLException
//    {
//        LocalTime startTime = LocalTime.of(6, 0, 0, 0);
//        LocalTime endTime = LocalTime.of(12, 0, 0, 0);
//        String nodeID = manager.approveNode("ADN2018051304223", startTime, endTime);
//        manager.changeNodeToActive(nodeID);
//
//        String query = "SELECT * FROM NODE_CALENDER WHERE NODE_ID = '" + nodeID + "'";
//        ResultSet resultSet = connection.executeQuery(query);
//        List<String> results = new ArrayList<>();
//        while (resultSet.next()) {
//            results.add(resultSet.getString(1));
//        }
//        for (String result : results) {
//            assertTrue(nodeHandler.isNodeActive(result));
//        }
//    }
//
//    @Test
//    public void testGetTotalActiveNodes() throws SQLException{
//
//        LocalTime startTime = LocalTime.of(6, 0, 0, 0);
//        LocalTime endTime = LocalTime.of(12, 0, 0, 0);
//        String nodeID = manager.approveNode("ADN2018051304223", startTime, endTime);
//        manager.changeNodeToActive(nodeID);
//
//        List<NodeInfo> nodeInfos = manager.viewAllNodes();
//        assertTrue(nodeInfos.size() > 0);
//    }
//    
    
}
