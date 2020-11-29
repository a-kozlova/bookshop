package com.kozlova.bookshop.validator;

import com.kozlova.bookshop.exception.InsufficientMoneyException;

public class CustomerValidator implements Validator<Double>{
    
    @Override
    public void validate(Double cash) {
        if(cash < 0) {
            throw new InsufficientMoneyException ("Customer is not allowed to have a negative account balance");
        }
    }

}
