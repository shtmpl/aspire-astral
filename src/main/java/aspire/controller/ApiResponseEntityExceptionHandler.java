package aspire.controller;

import aspire.service.OriginUnknownException;
import aspire.service.VacancyNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiResponseEntityExceptionHandler {

    @ExceptionHandler(OriginUnknownException.class)
    public ResponseEntity<String> handleOriginUnknown(OriginUnknownException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(VacancyNotFoundException.class)
    public ResponseEntity<String> handleVacancyNotFound(VacancyNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
