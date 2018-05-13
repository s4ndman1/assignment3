package za.ac.university.pretoria.node.api;

import java.util.List;

public interface Task {

    public List<String> getMetrics();
    public void setMetrics(List<String> metrics);
}
