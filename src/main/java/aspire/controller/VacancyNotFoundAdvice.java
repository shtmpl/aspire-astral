package aspire.controller;

import aspire.service.VacancyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class VacancyNotFoundAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(VacancyNotFoundException.class)
    String handleNotFound(VacancyNotFoundException exception) {
        return exception.getMessage();
    }

}
