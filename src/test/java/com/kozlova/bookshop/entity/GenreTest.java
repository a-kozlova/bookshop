package com.kozlova.bookshop.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.kozlova.bookshop.exception.GenreTypeException;

class GenreTest {

    @Test
    void valueOfShouldReturnGenreIfRightIndexPassed() {
        Genre expected = Genre.ADVENTURE;

        Genre actual = Genre.valueOf(0);

        assertThat(actual, equalTo(expected));
    }

    @Test
    void valueOfShouldThrowExceptionIfWrongIndexPassed() {
        GenreTypeException thrown = assertThrows(GenreTypeException.class, () -> Genre.valueOf(4));

        assertThat(thrown.getMessage(), equalTo("This genre is not defined"));
    }

}
