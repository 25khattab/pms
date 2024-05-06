package org.trainopia.pms.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandling.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<Object> errors = new ArrayList<>();
        errors.add(exception.getLocalizedMessage());
        ApiError apiError = new ApiError("Validation Failed Check Errors for more info", errors, HttpStatus.BAD_REQUEST.value());
        logger.error("Validation Failed Check Errors for more info", exception);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers,
                                                                            HttpStatusCode status, WebRequest request) {
        List<Object> errors = new ArrayList<>();
        ex.getAllValidationResults().forEach((error) -> {

            String fieldName = error.getMethodParameter().getParameterName();
            String errorMessage = error.getResolvableErrors().get(0).getDefaultMessage();
            errors.add(fieldName + ": " + errorMessage);
        });
        ApiError apiError = new ApiError("Validation Failed Check Errors for more info", errors, HttpStatus.BAD_REQUEST.value());
        logger.error("Validation Failed Check Errors for more info", ex);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<Object> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + ": " + errorMessage);
        });
        ApiError apiError = new ApiError("Validation Failed Check Errors for more info", errors, HttpStatus.BAD_REQUEST.value());
        logger.error("Validation Failed Check Errors for more info", exception);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleException(AppException exception) {
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), Collections.singletonList(exception.getErrors()),
                exception.getStatus().value());
        logger.error(exception.getClassName() + " -> " + exception.getFunctionName()
                + " -> " + exception.getLocalizedMessage(), exception);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exception) {
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), CommonError.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error(exception.getLocalizedMessage(), exception);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
