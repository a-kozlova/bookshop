package com.kozlova.bookshop.validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.kozlova.bookshop.exception.InsufficientMoneyException;

class CashValidatorTest {
    
    private Validator<Double> validator = new CashValidator();
    
    @Test
    void validateShouldThrowExceptionIfCashGetNegative() {
        InsufficientMoneyException thrown = assertThrows(InsufficientMoneyException.class,
                () -> validator.validate(-1.5));

        assertThat(thrown.getMessage(), equalTo("Customer is not allowed to have a negative account balance"));
    }
    
    @Test
    void validateShouldThrowExceptionIfCashIsNull() {
        InsufficientMoneyException thrown = assertThrows(InsufficientMoneyException.class,
                () -> validator.validate(null));

        assertThat(thrown.getMessage(), equalTo("Cash is null"));
    }

    @Test
    void validateShouldNotThrowAnyExceptionsIfCashIsNotNegative() {
        assertAll(() -> validator.validate(0.0));
        assertAll(() -> validator.validate(100.0));
    }
}
