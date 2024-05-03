package org.hca.blogproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    NOT_FOUND(5000,"Not Found",HttpStatus.I_AM_A_TEAPOT),
    DATABASE_ERROR(5001,"",HttpStatus.I_AM_A_TEAPOT),
    FIELD_ERROR(5002,"",HttpStatus.I_AM_A_TEAPOT),
    EMAIL_NOT_VALID(5003,"Email is not valid",HttpStatus.I_AM_A_TEAPOT),
    NULL_FIELD(5004,"This field can not be null.",HttpStatus.I_AM_A_TEAPOT),

    USER_NOT_FOUND(5100,"User not found",HttpStatus.I_AM_A_TEAPOT),
    EMAIL_ALREADY_EXISTS(5101,"Email already exists",HttpStatus.I_AM_A_TEAPOT),
    USER_DELETED(5102,"User deleted, unreachable.",HttpStatus.I_AM_A_TEAPOT),
    USER_ALREADY_ACTIVE(5103,"User is already active, no changes have been made.",HttpStatus.I_AM_A_TEAPOT),
    CATEGORY_DELETED(5200,"Category deleted, unreachable.",HttpStatus.I_AM_A_TEAPOT),
    CATEGORY_NOT_FOUND(5201,"Category not found",HttpStatus.I_AM_A_TEAPOT),
    CATEGORY_ALREADY_EXISTS(5202,"Category already exists",HttpStatus.I_AM_A_TEAPOT),
    POST_NOT_FOUND(5300,"Post not found",HttpStatus.I_AM_A_TEAPOT),
    POST_DELETED(5301,"Post deleted, unreachable.",HttpStatus.I_AM_A_TEAPOT),
    POST_ALREADY_LIKED(5302,"Post already liked by this user.",HttpStatus.I_AM_A_TEAPOT),
    POST_NOT_LIKED(5303,"Post has not been liked by the user, unlike is not allowed.",HttpStatus.I_AM_A_TEAPOT),
    COMMENT_NOT_FOUND(5400,"Comment not found",HttpStatus.I_AM_A_TEAPOT);


    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

}
