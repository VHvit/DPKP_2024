package com.example.exception;

import com.example.model.ApiErrorResponse;
import com.example.model.enums.ErrorCode;
import com.example.model.exceptions.GenericBadRequestException;
import com.example.model.exceptions.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionMapper {

    public ResponseEntity<ApiErrorResponse> handleException(HttpStatus status, String message, ErrorCode code) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse()
                .setCode(code)
                .setMessage(message);
        return ResponseEntity.status(status).body(apiErrorResponse);
    }

    @ExceptionHandler(GenericException.class) //404
    public ResponseEntity<ApiErrorResponse> handleGenericException(GenericException ex) {
        return handleException(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getCode());
    }

    @ExceptionHandler(GenericBadRequestException.class) // 400
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(GenericBadRequestException ex) {
        return handleException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getCode());
    }
}
