package org.hca.blogproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    NOT_FOUND(5000, "Not Found", HttpStatus.NOT_FOUND),
    DATABASE_ERROR(5001, "", HttpStatus.INTERNAL_SERVER_ERROR),
    FIELD_ERROR(5002, "", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_VALID(5003, "Email is not valid", HttpStatus.BAD_REQUEST),
    EMPTY_FIELD(5004, "This field can not be empty.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(5100, "User not found", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS(5101, "Email already exists", HttpStatus.BAD_REQUEST),
    USER_DELETED(5102, "User deleted, unreachable.", HttpStatus.GONE),
    USER_ALREADY_ACTIVE(5103, "User is already active, no changes have been made.", HttpStatus.BAD_REQUEST),
    CATEGORY_DELETED(5200, "Category deleted, unreachable.", HttpStatus.GONE),
    CATEGORY_NOT_FOUND(5201, "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_ALREADY_EXISTS(5202, "Category already exists", HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND(5300, "Post not found", HttpStatus.NOT_FOUND),
    POST_DELETED(5301, "Post deleted, unreachable.", HttpStatus.GONE),
    POST_ALREADY_LIKED(5302, "Post already liked by this user.", HttpStatus.BAD_REQUEST),
    POST_NOT_LIKED(5303, "Post has not been liked by the user, unlike is not allowed.", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND(5400, "Comment not found", HttpStatus.NOT_FOUND);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

}
