package com.kozlova.bookshop.exception;

public class IllegalIsbnException extends RuntimeException {

    private static final long serialVersionUID = -7776476656512107927L;

    public IllegalIsbnException() {
        super();
    }
    
    public IllegalIsbnException(String message) {
        super(message);
    }

}
