package com.kozlova.bookshop.validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.kozlova.bookshop.entity.Book;
import com.kozlova.bookshop.exception.IllegalIsbnException;

class IsbnValidatorTest {
    private Validator<Book> validator = new IsbnValidator();

    @Test
    void validateShouldThrowExceptionIfIsbnIsNull() {
        Book book = Book.builder().withIsbn(null).build();

        IllegalIsbnException thrown = assertThrows(IllegalIsbnException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("ISBN is null"));
    }

    @Test
    void validateShouldThrowExceptionIfIsbnIsEmptyString() {
        Book book = Book.builder().withIsbn("").build();

        IllegalIsbnException thrown = assertThrows(IllegalIsbnException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("ISBN is an empty string"));
    }
    
    @Test
    void validateShouldThrowExceptionIfIsbnHasWrongDigitAmount() {
        Book book = Book.builder().withIsbn("978-384135180").build();

        IllegalIsbnException thrown = assertThrows(IllegalIsbnException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("ISBN has no 13 symbols"));
    }

    @Test
    void validateShouldThrowExceptionIfIsbnIsWrong() {
        String invalidIsbn = "978-3442261747";
        Book book = Book.builder().withIsbn(invalidIsbn).build();

        IllegalIsbnException thrown = assertThrows(IllegalIsbnException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("Invalid ISBN-13 number"));
    }

    @Test
    void validateShouldThrowNoExceptionsIfIsbnIsCorrect() {
        String validIsbn = "978-3841335180";
        Book book = Book.builder().withIsbn(validIsbn).build();
        validator.validate(book);

        assertAll(() -> validator.validate(book));
    }
}
