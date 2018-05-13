package za.ac.university.pretoria.node.mvc.model;

import za.ac.university.pretoria.node.api.Task;

import java.util.List;

public class TaskImpl implements Task {

    List<String> metrics;

    @Override
    public List<String> getMetrics() {
        return metrics;
    }

    @Override
    public void setMetrics(List<String> metrics) {
        metrics = metrics;
    }

}
