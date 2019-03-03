package aspire.domain;

public class VacancyNotFoundException extends RuntimeException {

    public VacancyNotFoundException() {
    }

    public VacancyNotFoundException(String message) {
        super(message);
    }

    public VacancyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VacancyNotFoundException(Throwable cause) {
        super(cause);
    }

}
