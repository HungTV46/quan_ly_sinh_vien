package com.example.QuanLySinhVien.exception;

import com.example.QuanLySinhVien.dto.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.nio.file.AccessDeniedException;
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
        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
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
                            errorCode.getMessage(),
                            errorCode.getStatusCode()
                            );
                })
                .toList();

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errors.get(0).getStatusCode().value())
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
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiResponse<?>> handleOptimisticLockException(OptimisticLockException e) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(ErrorCode.CONFLICT.getCode())
                .message(ErrorCode.CONFLICT.getMessage())
                .build();
        return ResponseEntity.status(ErrorCode.CONFLICT.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFoundException(EntityNotFoundException e) {
        ApiResponse<?> apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.ENTITY_NOT_FOUND.getCode());
        apiResponse.setMessage(ErrorCode.ENTITY_NOT_FOUND.getMessage());

        return ResponseEntity.status(ErrorCode.ENTITY_NOT_FOUND.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AuthorizationDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }
}
