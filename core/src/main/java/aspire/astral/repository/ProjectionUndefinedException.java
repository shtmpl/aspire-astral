package aspire.astral.repository;

public class ProjectionUndefinedException extends RuntimeException {

    public ProjectionUndefinedException() {
        super();
    }

    public ProjectionUndefinedException(String message) {
        super(message);
    }

    public ProjectionUndefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectionUndefinedException(Throwable cause) {
        super(cause);
    }
}
