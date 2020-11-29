package com.kozlova.bookshop.entity;

import com.kozlova.bookshop.exception.GenreTypeException;

public enum Genre {
    ADVENTURE, BIOGRAPHY, COMIC, FANTASY;

    public static Genre valueOf(int index) {
        switch (index) {
        case 0:
            return ADVENTURE;
        case 1:
            return BIOGRAPHY;
        case 2:
            return COMIC;
        case 3:
            return FANTASY;
        default:
            throw new GenreTypeException("This genre is not defined");    
        }
        
    }
}
