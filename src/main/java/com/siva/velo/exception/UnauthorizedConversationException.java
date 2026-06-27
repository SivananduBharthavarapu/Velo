package com.siva.velo.exception;

public class UnauthorizedConversationException extends RuntimeException{
    public UnauthorizedConversationException(String message) {
        super(message);
    }
}
