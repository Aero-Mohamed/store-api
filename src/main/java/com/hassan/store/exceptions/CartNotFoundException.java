package com.hassan.store.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Cart not found");
    }
}
