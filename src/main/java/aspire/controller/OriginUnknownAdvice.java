package aspire.controller;

import aspire.service.OriginUnknownException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OriginUnknownAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OriginUnknownException.class)
    String handleOriginUnknown(OriginUnknownException exception) {
        return exception.getMessage();
    }

}
