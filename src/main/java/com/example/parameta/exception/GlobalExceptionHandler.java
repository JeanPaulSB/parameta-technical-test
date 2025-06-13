package com.example.parameta.exception;

import com.example.parameta.exception.custom.DuplicatedException;
import com.example.parameta.exception.custom.UnderageException;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exceptions under the HTTP Response BAD REQUEST (400).
     * @param ex Runtime exception.
     * @return Error message with HttpStatus BAD REQUEST.
     */
    @ExceptionHandler(value = {
            UnderageException.class
    })
    public ResponseEntity<Object> handleBadRequestExceptions(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    /**
     * Exceptions under the HTTP Response CONFLICT (409).
     * @param ex Runtime exception.
     * @return Error message with HttpStatus CONFLICT.
     */
    @ExceptionHandler({
            DuplicatedException.class,

    })
    public ResponseEntity<Object> handleAlreadyExistsException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /*
     * Overrides the default behaviour for invalid inputs caught by the @Valid
     * annotation within the controller parameters.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
