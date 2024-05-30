package org.trainopia.pms.utility;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandling {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandling.class);

    @ExceptionHandler({ JwtException.class })
    public ResponseEntity<?> handleJwtException(JwtException exception) {
        ApiError apiError = new ApiError("JWT Token Validation Failed Check Errors for more info",
                                         Collections.singletonList(exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED.value());
        logger.error("JWT Token Validation Failed Check Errors for more info", exception);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ AppException.class })
    public ResponseEntity<?> handleAppException(AppException exception) {
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), Collections.singletonList(exception.getErrors()),
                                         exception.getStatus().value());
        logger.error("{} -> {} -> {}", exception.getClassName(), exception.getFunctionName(), exception.getLocalizedMessage(), exception);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        ApiError apiError = new ApiError("Authentication Failed Check Errors for more info", Collections.singletonList(ex.getLocalizedMessage()),
                                         HttpStatus.FORBIDDEN.value());
        logger.error("Authentication Failed Check Errors for more info", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception ex) {
        ApiError apiError;
        if (ex instanceof HttpMessageNotReadableException exception) {
            List<Object> errors = new ArrayList<>();
            errors.add(exception.getLocalizedMessage());
            apiError = new ApiError("Validation Failed Check Errors for more info", errors, HttpStatus.BAD_REQUEST.value());
            logger.error("Validation Failed Check Errors for more info", ex);
        } else if (ex instanceof MethodArgumentNotValidException exception) {
            List<Object> errors = new ArrayList<>();
            exception.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.add(fieldName + ": " + errorMessage);
            });
            apiError = new ApiError("Validation Failed Check Errors for more info", errors, HttpStatus.BAD_REQUEST.value());
            logger.error("Validation Failed Check Errors for more info", ex);
        } else if (ex instanceof HandlerMethodValidationException exception) {
            List<Object> errors = new ArrayList<>();
            exception.getAllValidationResults().forEach((error) -> {
                String fieldName = error.getMethodParameter().getParameterName();
                String errorMessage = error.getResolvableErrors().get(0).getDefaultMessage();
                errors.add(fieldName + ": " + errorMessage);
            });
            apiError = new ApiError("Validation Failed Check Errors for more info", errors, HttpStatus.BAD_REQUEST.value());
            logger.error("Validation Failed Check Errors for more info", ex);
        } else if (ex instanceof ExpiredJwtException exception) {
            List<Object> errors = new ArrayList<>();
            errors.add(exception.getLocalizedMessage());
            apiError = new ApiError("Expired JWT Token", errors, HttpStatus.FORBIDDEN.value());
            logger.error("Expired JWT Token", ex);
        } else {
            apiError = new ApiError(ex.getLocalizedMessage(), CommonError.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
            logger.error(ex.getLocalizedMessage(), ex);
        }

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
