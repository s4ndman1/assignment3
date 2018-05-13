package za.ac.university.pretoria.node.task;

import java.util.List;

public interface Task {

    public List<String> getMetrics();
    public void setMeasurement(String measurement);
    public String execute();
}
