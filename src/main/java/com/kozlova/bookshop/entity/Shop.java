package com.kozlova.bookshop.entity;

import java.util.List;

public interface Shop {

    String getName();
    
    void setName(String name);

    int getSales();

    void setSales(int sales);

    List<Book> getBooks();

    void addBooks(List<Book> books);

    void addNewBook(Book book);

    Book getBookByTitle(String title);

    void sale(Book book);

    void showAllBooksWithoutDuplicates();

    void showBooksFilteredBy(Genre genre);

    boolean compareBooksTo(Shop otherShop);

}
