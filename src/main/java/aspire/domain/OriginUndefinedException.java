package aspire.domain;

public class OriginUndefinedException extends RuntimeException {

    public OriginUndefinedException() {
        super();
    }

    public OriginUndefinedException(String message) {
        super(message);
    }

    public OriginUndefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OriginUndefinedException(Throwable cause) {
        super(cause);
    }

}
