package com.kozlova.bookshop.exception;

public class BookNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8981966552171896183L;

    public BookNotFoundException() {
        super();
    }
    
    public BookNotFoundException(String message) {
        super(message);
    }
    
}
