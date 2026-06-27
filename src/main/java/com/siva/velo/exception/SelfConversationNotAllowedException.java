package com.siva.velo.exception;

public class SelfConversationNotAllowedException extends RuntimeException{
    public SelfConversationNotAllowedException(String message) {
        super(message);
    }
}
