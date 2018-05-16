package za.ac.university.pretoria.node.mvc.model.Report;

import java.util.ArrayList;
import java.util.List;

public class Report {

    String taskId;
    String dispatcher;
    List<ValueMetaData> result;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public List<ValueMetaData> getResult() {
        if (result == null)
            result = new ArrayList<>();
        return result;
    }

    public void setResult(List<ValueMetaData> result) {
        this.result = result;
    }
}
