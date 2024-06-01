package com.switchwon.error;

public class ChargeHistoryNotFoundException extends ResourceNotFoundException{

    private static final String DEFAULT_MESSAGE = "Charge History not found";
    public ChargeHistoryNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ChargeHistoryNotFoundException(String message) {
        super(message);
    }
}
