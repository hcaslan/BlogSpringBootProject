package org.hca.blogproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    USER_NOT_FOUND(5000,"User not found",HttpStatus.I_AM_A_TEAPOT),
    EMAIL_ALREADY_EXISTS(5001,"Email already exists",HttpStatus.I_AM_A_TEAPOT),
    USER_DELETED(5002,"User deleted, unreachable.",HttpStatus.I_AM_A_TEAPOT),
    CATEGORY_DELETED(5003,"Category deleted, unreachable.",HttpStatus.I_AM_A_TEAPOT),
    CATEGORY_NOT_FOUND(5004,"Category not found",HttpStatus.I_AM_A_TEAPOT),
    CATEGORY_ALREADY_EXISTS(5005,"Category already exists",HttpStatus.I_AM_A_TEAPOT),

    USERNAME_OR_PASSWORD_WRONG(5002,
            "Kullanıcı adı veya parola yanlış.",
            HttpStatus.I_AM_A_TEAPOT),
    URUN_NOT_FOUND(5004,"Urun not found",HttpStatus.I_AM_A_TEAPOT),
    NO_SATIS_CREATED(5005,"Satis could not be created",HttpStatus.I_AM_A_TEAPOT),
    PASSWORDS_NOT_MATCH(5006,"Passwords do not match",HttpStatus.I_AM_A_TEAPOT);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

}
