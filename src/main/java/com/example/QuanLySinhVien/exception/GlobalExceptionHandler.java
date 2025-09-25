package com.example.QuanLySinhVien.exception;

import com.example.QuanLySinhVien.dto.response.ApiResponse;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException (AppException e){
        ErrorCode errorCode = e.getErrorCode();

        ApiResponse<?> response = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(e.getCustomMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ApiResponse.ErrorItem> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    String enumKey = fieldError.getDefaultMessage();

                    ErrorCode errorCode ;

                    try {
                        errorCode = ErrorCode.valueOf(enumKey);
                    } catch (IllegalArgumentException ex) {
                        errorCode = ErrorCode.CLASSNAME_EXISTED;
                    }
                    return new ApiResponse.ErrorItem(
                            fieldError.getField(),
                            errorCode.getCode(),
                            errorCode.getMessage()
                            );
                })
                .toList();

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("validation error")
                .errorItems(errors)
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
        log.error("Exception: ", e);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiResponse<?>> handleOptimisticLockException(OptimisticLockException e) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(ErrorCode.CONFLICT.getCode())
                .message(ErrorCode.CONFLICT.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
}
