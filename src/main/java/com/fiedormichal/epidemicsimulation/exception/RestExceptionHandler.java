package com.fiedormichal.epidemicsimulation.exception;

import com.fiedormichal.epidemicsimulation.apierror.ApiError;
import com.fiedormichal.epidemicsimulation.apierror.ApiErrorMsg;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(String.format("Could not find the %s method for URL %s",
                ex.getHttpMethod(), ex.getRequestURL()));
        return buildResponseEntity(getApiError(HttpStatus.NOT_FOUND, errors, ApiErrorMsg.METHOD_NOT_FOUND.getValue()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        List<String> errors = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(type -> builder.append(type).append(", "));
        errors.add(builder.toString());

        return buildResponseEntity(getApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, errors, ApiErrorMsg.UNSUPPORTED_MEDIA_TYPE.getValue()));
    }

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentMismatch(Exception ex){
        List<String>errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return buildResponseEntity(getApiError(HttpStatus.BAD_REQUEST, errors, ApiErrorMsg.MISMATCH_TYPE.getValue()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleAll(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getLocalizedMessage());

        return buildResponseEntity(getApiError(HttpStatus.BAD_REQUEST,errors, ApiErrorMsg.ERROR_OCCURRED.getValue()));
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private ApiError getApiError(HttpStatus status, List<String>errors,  String message){
        return new ApiError(LocalDateTime.now(), status, message, errors);
    }
}

