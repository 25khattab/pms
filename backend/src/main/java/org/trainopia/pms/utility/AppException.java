package org.trainopia.pms.utility;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
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
