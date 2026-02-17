package com.hassan.store.exceptions;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException() {
        super("Cart is empty");
    }
}
