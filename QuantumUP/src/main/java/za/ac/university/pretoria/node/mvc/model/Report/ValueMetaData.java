package za.ac.university.pretoria.node.mvc.model.Report;

import java.util.ArrayList;
import java.util.List;

public class ValueMetaData {


    String measurement;
    List<Value> values;

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public List<Value> getValues() {
        if (values == null)
            values = new ArrayList<>();
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

}

