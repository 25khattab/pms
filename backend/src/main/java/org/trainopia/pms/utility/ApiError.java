package org.trainopia.pms.utility;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class ApiError {
    private String message;
    private int status;
    private LocalDateTime timeStamp;
    private List<Object> errors;

    public ApiError(String message, CommonError error, int status) {
        this.message = message;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.errors = Collections.singletonList(error);
    }

    public ApiError(String message, List<Object> errors, int status) {
        this.message = message;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.errors = errors;
    }

}
