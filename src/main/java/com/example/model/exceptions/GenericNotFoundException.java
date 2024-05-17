package com.example.model.exceptions;

import com.example.model.enums.ErrorCode;

import java.util.UUID;

public class GenericNotFoundException extends GenericException {

    public GenericNotFoundException(ErrorCode code, UUID entityId) {
        super(code, "Entity with id " + entityId + " not found");
    }

    public GenericNotFoundException(ErrorCode code, String entityName) {
        super(code, entityName + " not found");
    }

}
