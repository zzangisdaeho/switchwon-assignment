package com.switchwon.error;

public class UserNotFoundException extends ResourceNotFoundException{

    private static final String DEFAULT_MESSAGE = "User not found";
    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
