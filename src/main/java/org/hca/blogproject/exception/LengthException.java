package org.hca.blogproject.exception;

import lombok.Getter;

@Getter
public class LengthException extends RuntimeException {
    private ErrorType errorType;

    public LengthException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public LengthException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
