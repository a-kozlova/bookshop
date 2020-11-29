package com.kozlova.bookshop.entity;

import java.util.List;

public interface User<T> {
    
    String getName();

    void setName(String name);

    double getCash();

    void setCash(double cash);
    
    boolean getIsOwner();
    
    void setIsOwner(boolean isOwner);
    
    void buyItemByShop(String title, Shop shop);

    List<Book> getCollection();
    
    void addAll(List<T> collection);
    
    void addItem(T item);

}
