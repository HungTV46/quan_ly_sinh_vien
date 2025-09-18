package com.example.QuanLySinhVien.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
