package com.fiedormichal.epidemicsimulation.exception;

import com.fiedormichal.epidemicsimulation.apierror.ApiError;
import com.fiedormichal.epidemicsimulation.apierror.ApiErrorMsg;
import javassist.NotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String>errors;
        errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return buildResponseEntity(getApiError(HttpStatus.BAD_REQUEST, errors, ApiErrorMsg.VALIDATION_ERRORS.getValue()));
    }

    @ExceptionHandler(InitialDataNotFoundException.class)
    protected ResponseEntity<Object> handleInitialDataNotFound(Exception ex){
        List<String>errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return buildResponseEntity(getApiError(HttpStatus.NOT_FOUND, errors, ApiErrorMsg.INITIAL_DATA_NOT_FOUND.getValue()));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private ApiError getApiError(HttpStatus status, List<String>errors,  String message){
        return new ApiError(LocalDateTime.now(), status, message, errors);
    }
}

