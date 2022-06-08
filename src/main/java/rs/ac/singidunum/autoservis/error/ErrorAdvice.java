package rs.ac.singidunum.autoservis.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorObject handleRuntimeException(RuntimeException exception, HttpServletRequest request) {
        exception.printStackTrace();
        return ErrorObject.builder()
                .message(exception.getMessage())
                .path(request.getServletPath())
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .build();
    }
}
