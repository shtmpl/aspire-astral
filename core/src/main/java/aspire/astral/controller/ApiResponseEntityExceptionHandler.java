package aspire.astral.controller;

import aspire.astral.controller.response.ResponseError;
import aspire.astral.domain.OriginUndefinedException;
import aspire.astral.domain.VacancyNotFoundException;
import aspire.astral.integration.IntegrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiResponseEntityExceptionHandler {

    @ExceptionHandler(OriginUndefinedException.class)
    public ResponseEntity<ResponseError> handleOriginUnknownException(OriginUndefinedException exception) {
        ResponseError response = new ResponseError();
        response.setCode("origin.unknown");
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
}
