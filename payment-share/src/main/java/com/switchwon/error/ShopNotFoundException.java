package com.switchwon.error;

public class ShopNotFoundException extends ResourceNotFoundException{

    private static final String DEFAULT_MESSAGE = "Shop not found";
    public ShopNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ShopNotFoundException(String message) {
        super(message);
    }
}
