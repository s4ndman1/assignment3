package za.ac.university.pretoria.node.mvc.model.Report;

public class Value {

    private String Timestamp;
    private String value;

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
