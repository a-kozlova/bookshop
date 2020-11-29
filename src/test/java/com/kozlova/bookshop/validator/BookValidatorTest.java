package com.kozlova.bookshop.validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.kozlova.bookshop.entity.Book;
import com.kozlova.bookshop.exception.BookNotFoundException;
import com.kozlova.bookshop.exception.IllegalIsbnException;
import com.kozlova.bookshop.exception.InvalidFieldException;

class BookValidatorTest {
    private Validator<Book> validator = new BookValidator();

    @Test
    void validateShouldThrowExceptionIfBookIsNull() {
        BookNotFoundException thrown = assertThrows(BookNotFoundException.class,
                () -> validator.validate(null));

        assertThat(thrown.getMessage(), equalTo("Book is null"));
    }
    
    @Test
    void validateShouldThrowExceptionIfPriceNegative() {
        Book book = Book.builder().withPages(11).withPrice(-1).withIsbn(null).build();

        InvalidFieldException thrown = assertThrows(InvalidFieldException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("Book cannot have negative price"));
    }
    
    @Test
    void validateShouldThrowExceptionIfPagesNotPositive() {
        Book book = Book.builder().withPages(0).withPrice(13).withIsbn(null).build();

        InvalidFieldException thrown = assertThrows(InvalidFieldException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("Book cannot have pages equal or less than 0"));
    }
    
    @Test
    void validateShouldThrowExceptionIfIsbnIsNull() {
        Book book = Book.builder().withPages(11).withPrice(13).withIsbn(null).build();

        IllegalIsbnException thrown = assertThrows(IllegalIsbnException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("ISBN is null"));
    }

    @Test
    void validateShouldThrowExceptionIfIsbnIsEmptyString() {
        Book book = Book.builder().withPages(11).withPrice(13).withIsbn("").build();

        IllegalIsbnException thrown = assertThrows(IllegalIsbnException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("ISBN is an empty string"));
    }
    
    @Test
    void validateShouldThrowExceptionIfIsbnHasWrongDigitAmount() {
        Book book = Book.builder().withPages(11).withPrice(13).withIsbn("978-384135180").build();

        IllegalIsbnException thrown = assertThrows(IllegalIsbnException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("ISBN has no 13 symbols"));
    }

    @Test
    void validateShouldThrowExceptionIfIsbnIsWrong() {
        String invalidIsbn = "978-3442261747";
        Book book = Book.builder().withPages(11).withPrice(13).withIsbn(invalidIsbn).build();

        IllegalIsbnException thrown = assertThrows(IllegalIsbnException.class,
                () -> validator.validate(book));

        assertThat(thrown.getMessage(), equalTo("Invalid ISBN-13 number"));
    }

    @Test
    void validateShouldThrowNoExceptionsIfIsbnIsCorrect() {
        String validIsbn = "978-3841335180";
        Book book = Book.builder().withPages(11).withPrice(13).withIsbn(validIsbn).build();
        validator.validate(book);

        assertAll(() -> validator.validate(book));
    }
}
