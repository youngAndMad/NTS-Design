package danekerscode.technicaltask.exception.handler;

import danekerscode.technicaltask.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.function.BiFunction;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handle(EntityNotFoundException e) {
        return withDetails.apply(e, NOT_FOUND);
    }

    @ExceptionHandler(EmailRegisteredException.class)
    ProblemDetail handle(EmailRegisteredException e) {
        return withDetails.apply(e, BAD_REQUEST);
    }

    @ExceptionHandler(FileOperationException.class)
    ProblemDetail handle(FileOperationException e) {
        return withDetails.apply(e, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail handle(BadCredentialsException e) {
        return withDetails.apply(e, UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidUserPropertiesException.class)
    ProblemDetail handle(InvalidUserPropertiesException e) {
        return withDetails.apply(e, BAD_REQUEST);
    }


    private final BiFunction<RuntimeException, HttpStatus, ProblemDetail> withDetails =
            (e, status) ->
                    ProblemDetail.forStatusAndDetail(status, e.getMessage());
}
