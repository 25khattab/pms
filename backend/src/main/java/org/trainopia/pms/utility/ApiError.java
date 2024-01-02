package org.trainopia.pms.utility;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ApiError {
    private HttpStatus status;
    private LocalDateTime timeStamp;
    private List<CommonError> errors;

    public ApiError(String message, CommonError error, HttpStatus status) {

        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.errors = Collections.singletonList(error);

    }

    public ApiError(String message, List<CommonError> errors, HttpStatus status) {
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<CommonError> getErrors() {
        return errors;
    }

    public void setErrors(List<CommonError> errors) {
        this.errors = errors;
    }

}