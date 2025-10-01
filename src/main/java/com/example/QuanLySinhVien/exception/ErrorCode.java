package com.example.QuanLySinhVien.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error!", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error!", HttpStatus.BAD_REQUEST),
    CONFLICT(1002, "Data was updated or deleted by another user.Please try again!", HttpStatus.CONFLICT),
    ENTITY_NOT_FOUND(1003, "Entity not found!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1004, "Unauthenticated!",  HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "You do not have permission!",  HttpStatus.FORBIDDEN),


    NAME_INVALID(1201, "Name is invalid!",  HttpStatus.BAD_REQUEST),

    PASSWORD_INVALID(1301, "Password is invalid!", HttpStatus.BAD_REQUEST),
    PASSWORD_LENGTH_INVALID(1302, "Password length is invalid!", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(1303, "UserName existed!", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1304, "UserName is invalid!", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_FOUND(1305, "UserName is not found!", HttpStatus.NOT_FOUND),

    CLASSNAME_EXISTED(1401, "Class name existed!", HttpStatus.BAD_REQUEST),
    CLASSNAME_NOTBlANK(1402, "Class name is not blank!", HttpStatus.BAD_REQUEST),
    CLASS_FULL(1403, "Class is already full. Please register another class!", HttpStatus.BAD_REQUEST),

    ID_NOTFOUND(1501, "%s not found!", HttpStatus.NOT_FOUND),
    ID_EXISTED(1502, "%s already existed!", HttpStatus.BAD_REQUEST),

    EMAIL_INVALID(1601, "Email is invalid!", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1602, "Email already existed!", HttpStatus.BAD_REQUEST),

    PHONE_INVALID(1701, "Phone number is invalid!", HttpStatus.BAD_REQUEST),

    MARK_INVALID(1801, "Mark is between 0 and 10!", HttpStatus.BAD_REQUEST),
    BIRTH_DATE_INVALID(1802, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private HttpStatusCode statusCode;

    public String formatMessage(Object... args) {
        return String.format(this.message, args);
    }
}
