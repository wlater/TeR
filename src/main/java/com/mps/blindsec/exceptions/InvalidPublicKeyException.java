package com.mps.blindsec.exceptions;

public class InvalidPublicKeyException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidPublicKeyException(String message) {
        super(message);
    }
}
