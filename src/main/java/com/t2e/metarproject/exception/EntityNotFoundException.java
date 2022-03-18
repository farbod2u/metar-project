package com.t2e.metarproject.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Entity with not found.");
    }

    public EntityNotFoundException(String message){
        super(message);
    }
}
