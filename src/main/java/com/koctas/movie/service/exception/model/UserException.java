package com.koctas.movie.service.exception.model;

/**
 * @author hkaynar on 28.11.2021
 */
public class UserException extends RuntimeException {

    protected Object[] args;

    protected String messageKey;

    public UserException(String message, String messageKey, Object[] args) {
        super(message);
        this.messageKey = messageKey;
        this.args = args;
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
