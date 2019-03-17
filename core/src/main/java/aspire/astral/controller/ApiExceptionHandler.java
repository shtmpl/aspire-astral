package aspire.astral.controller;

import aspire.astral.controller.response.ResponseError;
import aspire.astral.domain.RepositoryUndefinedException;
import aspire.astral.domain.VacancyNotFoundException;
import aspire.astral.integration.IntegrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(RepositoryUndefinedException.class)
    public ResponseEntity<ResponseError> handleRepositoryUndefinedException(RepositoryUndefinedException exception) {
        ResponseError response = new ResponseError();
        response.setCode("repository.undefined");
        response.setReason(exception.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(VacancyNotFoundException.class)
    public ResponseEntity<ResponseError> handleVacancyNotFoundException(VacancyNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ResponseError> handleIntegrationException(IntegrationException exception) {
        ResponseError response = new ResponseError();
        response.setCode("integration.failure");
        response.setReason(exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleUnexpectedException(Exception exception) {
        logger.error("Unexpected error occurred", exception);

        ResponseError response = new ResponseError();
        response.setCode("unexpected");
        response.setReason(exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
