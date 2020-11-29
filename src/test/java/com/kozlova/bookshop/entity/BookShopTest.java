package com.kozlova.bookshop.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;    
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kozlova.bookshop.exception.BookNotFoundException;

class BookShopTest {

    private Shop shop = new BookShop("TestShop");   
    private Book book;
    private Book fantasy;
    private Book biography;
    private Book comic;
    private final String title = "Test title";

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .withTitle(title)
                .withPrice(20)
                .withPages(240)
                .withGenre(Genre.FANTASY)
                .withIsbn("978-3608963762")
                .build();
        fantasy = Book.builder()
                .withTitle("Fantasy")
                .withPrice(20)
                .withPages(240)
                .withGenre(Genre.FANTASY)
                .withIsbn("978-3608963762")
                .build();
        biography = Book.builder()
                .withTitle("Biography")
                .withPrice(20)
                .withPages(240)
                .withGenre(Genre.BIOGRAPHY)
                .withIsbn("978-3608963762")
                .build();
        comic = Book.builder()
                .withTitle("Comic")
                .withPrice(25)
                .withPages(200)
                .withGenre(Genre.COMIC)
                .withIsbn("978-3608963762")
                .build();
    }
   
    @Test
    void addNewBookShouldAddBookToShopWhenValidBookPassed() {
        shop.addNewBook(book);
        
        assertThat(shop.getBooks(), hasItem(book)); 
        assertThat(shop.getBooks().size(), equalTo(1));
    }
    
    @Test
    void addNewBookShouldAddBookToShopSeveralTimesWhenValidBookPassed() {
        shop.addNewBook(book);
        shop.addNewBook(book);
        shop.addNewBook(book);
        
        assertThat(shop.getBooks(), hasItem(book)); 
        assertThat(shop.getBooks().size(), equalTo(3));
    }
    
    @Test
    void getBookByTitleShouldReturnBookWithGivenTitleIfThereIsOneInShop() {
        shop.addNewBook(book);
        
        Book actual = shop.getBookByTitle(title);
        
        assertThat(actual, equalTo(book)); 
        assertThat(actual.getTitle(), equalTo(title)); 
    }
    
    @Test
    void getBookByTitleShouldThrowExceptionIfThereIsNoBookInShop() {
        BookNotFoundException thrown = assertThrows(BookNotFoundException.class, () -> shop.getBookByTitle(title));   
        
        assertThat(thrown.getMessage(), equalTo("Book not found"));             
    }

    @Test
    void saleShouldRemoveSoldBookFromShop() {
        shop.addNewBook(book);
        
        shop.sale(book);
        
        assertThat(shop.getBooks(), not(hasItem(book)));
        assertThat(shop.getBooks().size(), equalTo(0));
    }
    
    @Test
    void saleShouldThrowNoBookExceptionIfThereIsNoBookInShop() {
        BookNotFoundException thrown = assertThrows(BookNotFoundException.class, () -> shop.sale(book));   
        
        assertThat(thrown.getMessage(), equalTo("Book not found"));  
        assertThat(shop.getBooks(), not(hasItem(book)));
        assertThat(shop.getBooks().size(), equalTo(0));
    }

    @Test
    void showAllBooksWithoutDuplicatesShouldProvideViewWithoutDuplicates() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Book duplicateOfBook = Book.builder()
                .withTitle("Test title")
                .withPrice(20)
                .withPages(240)
                .withGenre(Genre.FANTASY)
                .withIsbn("978-3608963762")
                .build();
        Book otherBook = Book.builder()
                .withTitle("Distinct")
                .withPrice(25)
                .withPages(200)
                .withGenre(Genre.FANTASY)
                .withIsbn("978-3608963762")
                .build();
        shop.addNewBook(book);
        shop.addNewBook(duplicateOfBook);
        shop.addNewBook(otherBook);
        String expected = otherBook.toString() +"\r\n" + book.toString() + "\r\n";

        shop.showAllBooksWithoutDuplicates();
        
        assertThat(outContent.toString(), equalTo(expected));
    }
    
    @Test
    void showAllBooksWithoutDuplicatesShouldProvideEmptyStringIfNoBooksInShop() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "";

        shop.showAllBooksWithoutDuplicates();
        
        assertThat(outContent.toString(), equalTo(expected));
    }
    
    @Test
    void showBooksFilteredByShouldProvideViewOfFilteredBooks() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        shop.addNewBook(fantasy);
        shop.addNewBook(biography);
        shop.addNewBook(comic);
        String expected = comic.toString() + "\r\n";

        shop.showBooksFilteredBy(Genre.COMIC);
        
        assertThat(outContent.toString(), equalTo(expected));
    }
    
    @Test
    void showAllBooksWithoutDuplicatesShouldProvideEmptyStringIfNoBooksOfGivenGenreInShop() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        shop.addNewBook(fantasy);
        shop.addNewBook(biography);
        shop.addNewBook(comic);
        String expected = "";

        shop.showBooksFilteredBy(Genre.ADVENTURE);
        
        assertThat(outContent.toString(), equalTo(expected));
    }

    @Test
    void compareBooksWithShopShouldReturnTrueIfAllBooksAreEqual() {
        shop.addNewBook(fantasy);
        shop.addNewBook(biography);
        shop.addNewBook(comic);
        Shop otherShop = new BookShop("Other shop");
        otherShop.addNewBook(fantasy);
        otherShop.addNewBook(biography);
        otherShop.addNewBook(comic);
        boolean expected = true;
        
        boolean actual = shop.compareBooksTo(otherShop);
        
        assertThat(actual, equalTo(expected));
    }

    @Test
    void compareBooksWithShopShouldReturnFalseIfBooksAreNotEqual() {
        shop.addNewBook(fantasy);
        shop.addNewBook(biography);
        shop.addNewBook(comic);
        Shop otherShop = new BookShop("Other shop");
        otherShop.addNewBook(fantasy);
        otherShop.addNewBook(biography);
        boolean expected = false;
        
        boolean actual = shop.compareBooksTo(otherShop);
        
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void compareBooksWithShopShouldReturnFalseIfThisShopHasNoBooks() {
        Shop otherShop = new BookShop("Other shop");
        otherShop.addNewBook(fantasy);
        otherShop.addNewBook(biography);
        otherShop.addNewBook(comic);
        boolean expected = false;
        
        boolean actual = shop.compareBooksTo(otherShop);
        
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void compareBooksWithShopShouldReturnFalseIfOtherShopHasNoBooks() {
        shop.addNewBook(fantasy);
        shop.addNewBook(biography);
        shop.addNewBook(comic);
        Shop otherShop = new BookShop("Other shop");
        boolean expected = false;
        
        boolean actual = shop.compareBooksTo(otherShop);
        
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void compareBooksWithShopShouldReturnTrueIfShopHasDuplicates() {
        Shop otherShop = new BookShop("Other shop");
        shop.addNewBook(book);
        shop.addNewBook(book);
        otherShop.addNewBook(book);
        boolean expected = true;
        
        boolean actual = shop.compareBooksTo(otherShop);
        
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void compareBooksWithShopShouldReturnFalseIfOtherShopIsNull() {
        boolean expected = false;
        
        boolean actual = shop.compareBooksTo(null);
        
        assertThat(actual, equalTo(expected));
    }
}
