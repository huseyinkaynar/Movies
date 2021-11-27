package com.koctas.movie.service.exception.model;

/**
 * @author hkaynar on 28.11.2021
 */
public class ModelRemoveException extends RuntimeException {

    protected Object[] args;

    protected String messageKey;

    public ModelRemoveException(String message, String messageKey, Object[] args) {
        super(message);
        this.messageKey = messageKey;
        this.args = args;
    }

    public ModelRemoveException(String message) {
        super(message);
    }

    public ModelRemoveException(Throwable cause) {
        super(cause);
    }

    public ModelRemoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
