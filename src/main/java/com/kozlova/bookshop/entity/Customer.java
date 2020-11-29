package com.kozlova.bookshop.entity;

import java.util.ArrayList;
import java.util.List;

import com.kozlova.bookshop.validator.CashValidator;
import com.kozlova.bookshop.validator.Validator;

public class Customer implements User<Book> {
    private String name;
    private double cash;
    private List<Book> bookCollection;
    private boolean isOwner = false;
    private final Validator<Double> validator;

    public Customer(String name) {
        this.name = name;
        this.cash = 0;
        this.bookCollection = new ArrayList<>();
        this.validator = new CashValidator();
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
        this.cash = cash;
        validator.validate(this.cash);
    }
    
    @Override
    public boolean getIsOwner() {
        return isOwner;
    }
    
    @Override
    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    @Override
    public List<Book> getCollection() {
        return bookCollection;
    }

    @Override
    public void addAll(List<Book> collection) {
        this.bookCollection.addAll(collection);
    }

    @Override
    public void addItem(Book book) {
        this.bookCollection.add(book);
    }

    @Override
    public void buyItemByShop(String title, Shop shop) {
        Book book = shop.getBookByTitle(title);
        pay(book.getPrice());
        shop.sale(book);
        addItem(book);
    }

    private void pay(double price) {
        this.cash -= price;
        validator.validate(Double.valueOf(cash));
    }

}
