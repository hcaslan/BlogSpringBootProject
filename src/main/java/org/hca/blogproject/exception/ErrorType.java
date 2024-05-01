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
    CATEGORY_DELETED(5100,"Category deleted, unreachable.",HttpStatus.I_AM_A_TEAPOT),
    CATEGORY_NOT_FOUND(5101,"Category not found",HttpStatus.I_AM_A_TEAPOT),
    CATEGORY_ALREADY_EXISTS(5102,"Category already exists",HttpStatus.I_AM_A_TEAPOT),
    POST_NOT_FOUND(5200,"Post not found",HttpStatus.I_AM_A_TEAPOT),
    POST_DELETED(5201,"Post deleted, unreachable.",HttpStatus.I_AM_A_TEAPOT),
    POST_ALREADY_LIKED(5202,"Post already liked by this user.",HttpStatus.I_AM_A_TEAPOT),
    POST_NOT_LIKED(5203,"Post has not been liked by the user, unlike is not allowed.",HttpStatus.I_AM_A_TEAPOT),
    COMMENT_NOT_FOUND(5300,"Comment not found",HttpStatus.I_AM_A_TEAPOT);


    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

}
