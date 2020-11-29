package com.kozlova.bookshop.exception;

public class InvalidFieldException extends RuntimeException {

    private static final long serialVersionUID = 136917762026027715L;

    public InvalidFieldException(String message) {
        super(message);
    }

}
