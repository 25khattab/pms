package org.trainopia.pms.utility;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class AppAuthenticationException extends AppException {

    public AppAuthenticationException(String functionName, String className, String message, CommonError error, HttpStatus status) {
        super(functionName, className, message, error, status);

    }

}
