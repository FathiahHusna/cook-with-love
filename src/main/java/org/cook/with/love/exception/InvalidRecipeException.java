package org.cook.with.love.exception;

public class InvalidRecipeException extends RuntimeException{
    private final String errorCode;

    public InvalidRecipeException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
