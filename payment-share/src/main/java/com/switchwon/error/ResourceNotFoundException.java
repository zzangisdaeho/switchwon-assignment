package com.switchwon.error;

public class ResourceNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Resource not found";

    public ResourceNotFoundException(){
        super(DEFAULT_MESSAGE);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
