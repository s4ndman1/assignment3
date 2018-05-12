package task;
import ac.za.university.pretoria.task.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockTask {

    Task task;
    TaskExecution taskExecution;
    @Before
    public void init(){
        taskExecution = new TaskExecution();


    }

    @Test
    public void testCPUUsage(){
        task = mock(Task.class);
        when(task.execute()).thenReturn("87%");
        when(task.getMetric()).thenReturn("CPU-Usage");

        Report report = taskExecution.execute(task);

        assertTrue(report.getMetric().equalsIgnoreCase("CPU-Usage"));
        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("87%"));
    }


    @Test
    public void testCPUTime(){
        task = mock(Task.class);
        when(task.execute()).thenReturn("10 seconds");
        when(task.getMetric()).thenReturn("CPU-TIME");

        Report report = taskExecution.execute(task);

        assertTrue(report.getMetric().equalsIgnoreCase("CPU-TIME"));
        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("10 seconds"));
    }



    @Test
    public void testElapsedTime(){
        task = mock(Task.class);
        when(task.execute()).thenReturn("20 seconds");
        when(task.getMetric()).thenReturn("Elapsed Time");

        Report report = taskExecution.execute(task);

        assertTrue(report.getMetric().equalsIgnoreCase("Elapsed Time"));
        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("20 seconds"));
    }

    @Test
    public void testMemoryUsage(){
        task = mock(Task.class);
        when(task.execute()).thenReturn("531Mb");
        when(task.getMetric()).thenReturn("Memory-Usage");

        Report report = taskExecution.execute(task);

        assertTrue(report.getMetric().equalsIgnoreCase("Memory-Usage"));
        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("531Mb"));
    }

    @Test
    public void TestEnergyGenerated(){
        task = mock(Task.class);
        when(task.execute()).thenReturn("1.0");
        when(task.getMetric()).thenReturn("Energy Generated");

        Report report = taskExecution.execute(task);

        assertTrue(report.getMetric().equalsIgnoreCase("Energy Generated"));
        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("1.0"));
    }

    @Test
    public void TestHeatGenerated(){
        task = mock(Task.class);
        when(task.execute()).thenReturn("1.0");
        when(task.getMetric()).thenReturn("Heat Generated");

        Report report = taskExecution.execute(task);

        assertTrue(report.getMetric().equalsIgnoreCase("Heat Generated"));
        assertTrue(report.getSingleMeasurement().equalsIgnoreCase("1.0"));
    }

}
