package za.ac.university.pretoria.node.mvc;

import za.ac.university.pretoria.node.api.Task;
import za.ac.university.pretoria.node.mvc.controller.NodeImpl;
import za.ac.university.pretoria.node.mvc.model.Report;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class mockTask {

    Task task;
    NodeImpl nodeImpl;
    @Before
    public void init() throws SQLException, ClassNotFoundException {
        nodeImpl = new NodeImpl();
        task = mock(Task.class);
        List<String> mockList = new ArrayList<>();

        mockList.add("energyGenerated");
        mockList.add("memoryUsage");
        mockList.add("cpuTime");
        mockList.add("cpuUsage");
        mockList.add("heatGenerated");

        when(task.getMetrics()).thenReturn(mockList);
    }

    @Test
    public void testCPUUsage(){

        Report report = nodeImpl.execute(task);


        assertTrue(report.getCpuTime().size() > 0);
        assertTrue(report.getCpuUsage().size() > 0);
        assertTrue(report.getElapsedTime() > 0);
        assertTrue(report.getEnergyGenerated().size() > 0);
        assertTrue(report.getHeatGenerated().size() > 0);
        assertTrue(report.getMemoryUsage().size() > 0);
    }

//
//    @Test
//    public void testCPUTime(){
//        task = mock(Task.class);
//        when(task.execute()).thenReturn("10 seconds");
//        when(task.getMetric()).thenReturn("CPU-TIME");
//
//        Report report = nodeImpl.execute(task);
//
//        assertTrue(report.getMetric().equalsIgnoreCase("CPU-TIME"));
//        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("10 seconds"));
//    }
//
//
//
//    @Test
//    public void testElapsedTime(){
//        task = mock(Task.class);
//        when(task.execute()).thenReturn("20 seconds");
//        when(task.getMetric()).thenReturn("Elapsed Time");
//
//        Report report = nodeImpl.execute(task);
//
//        assertTrue(report.getMetric().equalsIgnoreCase("Elapsed Time"));
//        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("20 seconds"));
//    }
//
//    @Test
//    public void testMemoryUsage(){
//        task = mock(Task.class);
//        when(task.execute()).thenReturn("531Mb");
//        when(task.getMetric()).thenReturn("Memory-Usage");
//
//        Report report = nodeImpl.execute(task);
//
//        assertTrue(report.getMetric().equalsIgnoreCase("Memory-Usage"));
//        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("531Mb"));
//    }
//
//    @Test
//    public void TestEnergyGenerated(){
//        task = mock(Task.class);
//        when(task.execute()).thenReturn("1.0");
//        when(task.getMetric()).thenReturn("Energy Generated");
//
//        Report report = nodeImpl.execute(task);
//
//        assertTrue(report.getMetric().equalsIgnoreCase("Energy Generated"));
//        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("1.0"));
//    }
//
//    @Test
//    public void TestHeatGenerated(){
//        task = mock(Task.class);
//        when(task.execute()).thenReturn("1.0");
//        when(task.getMetric()).thenReturn("Heat Generated");
//
//        Report report = nodeImpl.execute(task);
//
//        assertTrue(report.getMetric().equalsIgnoreCase("Heat Generated"));
//        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("1.0"));
//    }

}
