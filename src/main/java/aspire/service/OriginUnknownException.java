package aspire.service;

public class OriginUnknownException extends RuntimeException {

    public OriginUnknownException() {
        super();
    }

    public OriginUnknownException(String message) {
        super(message);
    }

    public OriginUnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public OriginUnknownException(Throwable cause) {
        super(cause);
    }

}
