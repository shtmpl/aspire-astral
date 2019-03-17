package aspire.astral.domain;

public class RepositoryUnsupportedOperationException extends RuntimeException {

    public RepositoryUnsupportedOperationException() {
        super();
    }

    public RepositoryUnsupportedOperationException(String message) {
        super(message);
    }

    public RepositoryUnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryUnsupportedOperationException(Throwable cause) {
        super(cause);
    }
}
