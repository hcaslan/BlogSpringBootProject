package org.hca.blogproject.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{
    private ErrorType errorType;

    public ValidationException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ValidationException(ErrorType errorType, String customMessage) {
        super(errorType.getMessage() + " " + customMessage);
        this.errorType = errorType;
    }
}
