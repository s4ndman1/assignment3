package za.ac.university.pretoria.node.mvc;

import com.google.gson.Gson;
import za.ac.university.pretoria.node.api.NodeHandler;
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
import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class mockNodeTest {

    Task task;
    Gson gson;
    NodeImpl nodeImpl;
    NodeHandler nodeHandler;

    @Before
    public void init() throws SQLException, ClassNotFoundException {
        task = mock(Task.class);
        nodeHandler = mock(NodeHandler.class);
        gson = new Gson();

        ArrayList<Measurement> measurements = new ArrayList<>();

        Measurement measurement = new Measurement();
        measurement.setName("Francois1");
        measurement.setID(1);
        measurements.add(measurement);

        Measurement measurement2 = new Measurement();
        measurement2.setName("Francois1");
        measurement2.setID(2);
        measurements.add(measurement2);

        when(task.getMeasurement()).thenReturn(measurements);

    }

    @Test
    public void testCPUUsage() {

        FinalReport finalReport = nodeImpl.execute(task);

        for (Report report : finalReport.getResult())
            for (ValueMetaData valueMetaData : report.getResult())
                for (Value value : valueMetaData.getValues()) {
                    assertFalse(value.getValue() == null);
                    assertFalse(value.getValue().isEmpty());
                }
    }


    @Test
    public void testReportToJson() {

        FinalReport report = nodeImpl.execute(task);
        String json = gson.toJson(report);
        assertFalse(json.isEmpty());
    }
//
//    @Test
//    public void testCPUTime(){
//        task = mock(Task.class);
//        when(task.execute()).thenReturn("10 seconds");
//        when(task.getMetric()).thenReturn("CPU-TIME");
//
//        ValueMetaData report = nodeImpl.execute(task);
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
//        ValueMetaData report = nodeImpl.execute(task);
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
//        ValueMetaData report = nodeImpl.execute(task);
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
//        ValueMetaData report = nodeImpl.execute(task);
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
//        ValueMetaData report = nodeImpl.execute(task);
//
//        assertTrue(report.getMetric().equalsIgnoreCase("Heat Generated"));
//        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("1.0"));
//    }

}
