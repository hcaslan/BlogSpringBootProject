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


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> handleBusinessException(ValidationException ex) {
        ErrorType errorType = ex.getErrorType();
        return new ResponseEntity<>(createErrorMessage(ex),
                errorType.getHttpStatus());
    }
    @ExceptionHandler(LengthException.class)
    public ResponseEntity<ErrorMessage> handleDataBaseException(LengthException ex) {
        ErrorType errorType = ex.getErrorType();
        return new ResponseEntity<>(createErrorMessage(ex),
                errorType.getHttpStatus());
    }

    private ErrorMessage createErrorMessage(ValidationException ex) {
        return ErrorMessage.builder()
                .code(ex.getErrorType().getCode())
                .message(ex.getMessage())
                .build();
    }

    private ErrorMessage createErrorMessage(LengthException ex) {
        return ErrorMessage.builder()
                .code(ex.getErrorType().getCode())
                .message(ex.getMessage())
                .build();
    }


}
