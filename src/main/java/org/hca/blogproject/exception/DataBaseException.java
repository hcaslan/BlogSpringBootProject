package org.hca.blogproject.exception;

import lombok.Getter;

import java.sql.SQLException;

@Getter
public class DataBaseException extends RuntimeException {
    private ErrorType errorType;

    public DataBaseException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public DataBaseException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
