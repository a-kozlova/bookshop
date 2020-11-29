package com.kozlova.bookshop.entity;

import java.util.ArrayList;
import java.util.List;

import com.kozlova.bookshop.validator.CustomerValidator;
import com.kozlova.bookshop.validator.Validator;

public class Customer implements User {
    private String name;
    private double cash;
    private List<Book> bookCollection;
    private final Validator<Double> validator;

    public Customer(String name) {
        this.name = name;
        this.cash = 0;
        this.bookCollection = new ArrayList<>();
        this.validator = new CustomerValidator();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getCash() {
        return cash;
    }

    @Override
    public void setCash(double cash) {
        validator.validate(cash);
        this.cash = cash;
    }

    @Override
    public List<Book> getBookCollection() {
        return bookCollection;
    }

    public void addAllBooksToCollection(List<Book> collection) {
        this.bookCollection.addAll(collection);
    }

    @Override
    public void buyBookByShop(String title, Shop shop) {
        Book book = shop.getBookByTitle(title);
        pay(book.getPrice());
        shop.sale(book);
        addBookToCollection(book);
    }

    private void pay(double price) {
        this.cash -= price;
        validator.validate(Double.valueOf(cash));
    }

    private void addBookToCollection(Book book) {
        this.bookCollection.add(book);
    }

}
