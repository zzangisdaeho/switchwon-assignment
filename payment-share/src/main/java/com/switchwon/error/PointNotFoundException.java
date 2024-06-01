package com.switchwon.error;

public class PointNotFoundException extends ResourceNotFoundException{

    private static final String DEFAULT_MESSAGE = "Point not found";
    public PointNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PointNotFoundException(String message) {
        super(message);
    }
}
