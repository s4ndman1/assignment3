package za.ac.university.pretoria.node.mvc.model;

public class NodeException extends Exception {

    public NodeException() {
        super();
    }

    public NodeException(String message) {
        super(message);
    }

    public NodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeException(Throwable cause) {
        super(cause);
    }

    protected NodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
