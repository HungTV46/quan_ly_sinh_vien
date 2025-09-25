package com.example.QuanLySinhVien.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error!"),
    INVALID_KEY(1001, "Uncategorized error!"),


    NAME_INVALID(1201, "Name is invalid!"),

    PASSWORD_INVALID(1301, "Password is invalid!"),
    PASSWORD_LENGTH_INVALID(1302, "Password length is invalid!"),
    USERNAME_EXISTED(1303, "UserName existed!"),
    USERNAME_INVALID(1304, "UserName is invalid!"),


    CLASSNAME_EXISTED(1401, "Class name existed!"),
    CLASSNAME_NOTBlANK(1402, "Class name is not blank!"),

    ID_NOTFOUND(1501, "%s not found!"),
    ID_EXISTED(1502, "%s already existed!"),

    EMAIL_INVALID(1601, "Email is invalid!"),
    EMAIL_EXISTED(1602, "Email already existed!"),

    PHONE_INVALID(1701, "Phone number is invalid!"),

    MARK_INVALID(1801, "Mark is between 0 and 10!"),
    ;

    private final int code;
    private final String message;

    public String formatMessage(Object... args) {
        return String.format(this.message, args);
    }
}
