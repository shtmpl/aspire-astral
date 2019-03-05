package aspire.astral.controller;

import aspire.astral.domain.OriginUndefinedException;
import aspire.astral.domain.VacancyNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiResponseEntityExceptionHandler {

    @ExceptionHandler(OriginUndefinedException.class)
    public ResponseEntity<String> handleOriginUnknown(OriginUndefinedException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(VacancyNotFoundException.class)
    public ResponseEntity<String> handleVacancyNotFound(VacancyNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
