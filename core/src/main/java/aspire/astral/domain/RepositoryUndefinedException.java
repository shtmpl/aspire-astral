package aspire.astral.domain;

public class RepositoryUndefinedException extends RuntimeException {

    public RepositoryUndefinedException() {
        super();
    }

    public RepositoryUndefinedException(String message) {
        super(message);
    }

    public RepositoryUndefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryUndefinedException(Throwable cause) {
        super(cause);
    }
}
