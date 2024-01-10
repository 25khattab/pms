package org.trainopia.pms.utility;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AppException extends RuntimeException {
    private String functionName;
    private String className;
    private HttpStatus status;
    private LocalDateTime timeStamp;
    private List<CommonError> errors;

    public AppException(String functionName, String className, String message, CommonError error, HttpStatus status) {
        super(message);
        this.functionName = functionName;
        this.className = className;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.errors = Collections.singletonList(error);

    }

    public AppException(String functionName, String className, String message, List<CommonError> errors, HttpStatus status) {
        super(message);
        this.functionName = functionName;
        this.className = className;
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

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "AppException{" +
                "functionName='" + functionName + '\'' +
                ", className='" + className + '\'' +
                ", status=" + status +
                ", timeStamp=" + timeStamp +
                ", errors=" + errors +
                '}';
    }
}
