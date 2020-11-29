package com.kozlova.bookshop.validator;

import com.kozlova.bookshop.entity.Book;
import com.kozlova.bookshop.exception.BookNotFoundException;
import com.kozlova.bookshop.exception.IllegalIsbnException;
import com.kozlova.bookshop.exception.InvalidFieldException;

public final class BookValidator implements Validator<Book> {

    public void validate(Book book) {
        
        if(book == null) {
            throw new BookNotFoundException("Book is null");
        }
        
        if(book.getPages() <= 0) {
            throw new InvalidFieldException("Book cannot have pages equal or less than 0");
        }

        if(book.getPrice() < 0) {
            throw new InvalidFieldException("Book cannot have negative price");
        }
        
        String isbn = book.getIsbn();

        if (isbn == null) {
            throw new IllegalIsbnException("ISBN is null");
        }

        isbn = isbn.replace("-", "");

        if (isbn.isEmpty()) {
            throw new IllegalIsbnException("ISBN is an empty string");
        }

        if (isbn.length() != 13) {
            throw new IllegalIsbnException("ISBN has no 13 symbols");
        }

        int[] isbnDigits = isbn.chars().map(i -> i - '0').toArray();
        int checkSum = isbnDigits[isbnDigits.length - 1];

        int checkDigit = 0;
        for (int i = 0; i < isbnDigits.length - 1; i++) {
            if (i % 2 == 0) {
                checkDigit += isbnDigits[i];
            } else {
                checkDigit += isbnDigits[i] * 3;
            }
        }
        checkDigit %= 10;
        checkDigit = (10 - checkDigit) % 10;

        if (checkDigit != checkSum) {
            throw new IllegalIsbnException("Invalid ISBN-13 number");
        }

    }

}
