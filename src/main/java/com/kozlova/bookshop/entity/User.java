package com.kozlova.bookshop.entity;

import java.util.List;

public interface User {

    String getName();

    void setName(String name);

    double getCash();

    void setCash(double cash);
    
    void buyBookByShop(String title, Shop shop);

    List<Book> getBookCollection();

}
