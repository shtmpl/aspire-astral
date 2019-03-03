package aspire.domain;

public class OriginUnsupportedOperationException extends RuntimeException {

    public OriginUnsupportedOperationException() {
        super();
    }

    public OriginUnsupportedOperationException(String message) {
        super(message);
    }

    public OriginUnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OriginUnsupportedOperationException(Throwable cause) {
        super(cause);
    }

}
