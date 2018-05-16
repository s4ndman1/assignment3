package za.ac.university.pretoria.node.mvc.model.Report;

import java.util.ArrayList;
import java.util.List;

public class FinalReport {

    List<Report> result;

    public List<Report> getResult() {
        if (result == null)
            result = new ArrayList<>();
        return result;
    }

    public void setResult(List<Report> result) {
        this.result = result;
    }
}
