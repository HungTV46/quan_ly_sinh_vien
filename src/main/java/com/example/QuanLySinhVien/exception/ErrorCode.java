package com.example.QuanLySinhVien.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error!"),
    INVALID_KEY(1001, "Uncategorized error!"),
    USERNAME_EXISTED(1002, "UserName existed!"),
    NAME_INVALID(1003, "Name is invalid!"),
    PASSWORD_INVALID(1004, "Password is invalid!"),
    CLASSNAME_EXISTED(1005, "Class name existed!"),
    CLASSNAME_NOTBlANK(1006, "Class name is not blank!"),
    ID_NOTFOUND(1007, "Id not found!"),
            ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
