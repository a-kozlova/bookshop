package com.kozlova.bookshop.exception;

public class InsufficientMoneyException extends RuntimeException{
    
    private static final long serialVersionUID = -2810472002216975734L;

    public InsufficientMoneyException(String message) {
        super(message);
    }
    
}
