package com.kozlova.bookshop.entity;

public enum Genre {
    ADVENTURE, BIOGRAPHY, COMIC, FANTASY;

    public static Genre valueOf(int genreNumber) {
        switch (genreNumber) {
        case 0:
            return ADVENTURE;
        case 1:
            return BIOGRAPHY;
        case 2:
            return COMIC;
        case 3:
            return FANTASY;
        default:
            throw new RuntimeException("Enum exception");    
        }
        
    }
}
