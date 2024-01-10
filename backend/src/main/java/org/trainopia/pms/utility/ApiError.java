package org.trainopia.pms.utility;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ApiError {
    private String message;
    private int status;
    private LocalDateTime timeStamp;
    private List<Object> errors;


    public ApiError(String message, CommonError error, int status) {
        this.message=message;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.errors = Collections.singletonList(error);

    }

    public ApiError(String message, List<Object> errors, int status) {
        this.message=message;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}