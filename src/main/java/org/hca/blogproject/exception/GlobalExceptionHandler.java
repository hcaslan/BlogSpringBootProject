package org.hca.blogproject.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleException() {
//        return ResponseEntity.badRequest().body("RunTime Exception occurred in the application................");
//    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {
        ErrorType errorType = ex.getErrorType();
        return new ResponseEntity<>(createErrorMessage(ex),
                errorType.getHttpStatus());
    }
    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErrorMessage> handleDataBaseException(DataBaseException ex) {
        ErrorType errorType = ex.getErrorType();
        return new ResponseEntity<>(createErrorMessage(ex),
                errorType.getHttpStatus());
    }

    private ErrorMessage createErrorMessage(BusinessException ex) {
        return ErrorMessage.builder()
                .code(ex.getErrorType().getCode())
                .message(ex.getMessage())
                .build();
    }

    private ErrorMessage createErrorMessage(DataBaseException ex) {
        return ErrorMessage.builder()
                .code(ex.getErrorType().getCode())
                .message(ex.getMessage())
                .build();
    }


}
