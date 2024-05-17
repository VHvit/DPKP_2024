package com.example.model.exceptions;

import com.example.model.enums.ErrorCode;

public class GenericBadRequestException extends GenericException {

    public GenericBadRequestException(ErrorCode code, String message) {
        super(code, message);
    }
}
