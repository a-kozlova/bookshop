package com.kozlova.bookshop.entity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.kozlova.bookshop.exception.BookNotFoundException;
import com.kozlova.bookshop.validator.BookValidator;
import com.kozlova.bookshop.validator.Validator;

public class BookShop implements Shop {
    private String name;
    private int sales;
    private List<Book> books;
    private final Validator<Book> validator;

    public BookShop(String name) {
        this.name = name;
        this.sales = 0;
        this.books = new LinkedList<>();
        this.validator = new BookValidator();
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
    public int getSales() {
        return sales;
    }

    @Override
    public void setSales(int sales) {
        this.sales = sales;
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public void addBooks(List<Book> books) {
        this.books.addAll(books);
    }

    @Override
    public void addNewBook(Book book) {
        validator.validate(book);
        books.add(book);
    }

    @Override
    public Book getBookByTitle(String title) {
        Optional<Book> optBook = books.stream()
                                      .filter(b -> title.equals(b.getTitle()))
                                      .findFirst();
        if (optBook.isPresent()) {
            return optBook.get();
        } else {
            throw new BookNotFoundException("Book not found");
        }
    }

    @Override
    public void sale(Book book) {
        if (books.contains(book)) {
            sales += book.getPrice();
            books.remove(book);
        } else {
            throw new BookNotFoundException("Book not found");
        }
    }

    @Override
    public void showAllBooksWithoutDuplicates() {
        TreeSet<Book> booksWithoutDuplicates = books.stream()
                .collect(Collectors.toCollection(TreeSet::new));
        booksWithoutDuplicates.forEach(book -> System.out.println(book.toString()));
    }

    @Override
    public void showBooksFilteredBy(Genre genre) {
        TreeSet<Book> filteredBooks = books.stream().filter(book -> book.getGenre() == genre)
                .collect(Collectors.toCollection(TreeSet::new));
        filteredBooks.forEach(book -> System.out.println(book.toString()));
    }

    @Override
    public boolean compareBooksTo(Shop otherShop) {
        if(otherShop == null) {
            return false;
        }
        
        HashSet<Book> thisBooks = books.stream().collect(Collectors.toCollection(HashSet::new));
        HashSet<Book> otherBooks = ((BookShop) otherShop).getBooks().stream()
                .collect(Collectors.toCollection(HashSet::new));

        return thisBooks.equals(otherBooks);
    }

}
