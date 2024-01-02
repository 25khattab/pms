package org.trainopia.pms.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandling.class);

    @ExceptionHandler
    public ResponseEntity<?> handleException(AppException exception) {
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), exception.getErrors(), exception.getStatus());
        logger.error(exception.getLocalizedMessage(),exception);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exception) {
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), CommonError.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        logger.error(exception.getLocalizedMessage(),exception);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
