package com.kozlova.bookshop;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.kozlova.bookshop.entity.Book;
import com.kozlova.bookshop.entity.BookShop;
import com.kozlova.bookshop.entity.Customer;
import com.kozlova.bookshop.entity.Genre;
import com.kozlova.bookshop.entity.Shop;
import com.kozlova.bookshop.entity.User;
import com.kozlova.bookshop.service.DataService;

public class ConsoleApplication {

    private static final String ACTION_SEPARATOR_LINE = "----------------------------------------------------------------";
    private static final String HEADER_LINE = "################################################################";
    private static final String INTERACTION_FORMAT = "%d: %s";
    private static final int EXIT_CODE = 0;

    private static final String[] appInteractions = { "Exit",
                                                      "Go to shop", 
                                                      "Login as owner" };
    private static final String[] shopInteractions = { "Back to start", 
                                                       "Show all books",
                                                       "Filter books by genre", 
                                                       "Buy a book", 
                                                       "Compare books with another shop",
                                                       "Add book to shop" };
    private static final String[] genres = { Genre.ADVENTURE.toString(), 
                                             Genre.BIOGRAPHY.toString(),
                                             Genre.COMIC.toString(), 
                                             Genre.FANTASY.toString() };

    public static void main(String[] args) {
        List<Shop> shops = createShops();

        try (Scanner scanner = new Scanner(System.in)) {
            User<Book> customer = createUser(scanner);

            int actionNumber;
            do {
                showSelection(appInteractions);
                actionNumber = userChoice(scanner, 0, appInteractions.length);
                if (actionNumber == 1) {
                    interactWithShops(shops, scanner, customer);
                } else if (actionNumber == 2) {
                    System.out.println("Authentification process ...");
                    customer.setIsOwner(true);
                    interactWithShops(shops, scanner, customer);
                }

            } while (actionNumber != EXIT_CODE);

            System.out.println("Bye!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static List<Shop> createShops() {
        List<Shop> shops = new LinkedList<>();
        Shop amazon = new BookShop("AmazonBooks");
        Shop bookStore = new BookShop("BookStore");
        Shop bookSellers = new BookShop("BookSellers");
        Shop rareBooks = new BookShop("Rare Books");
        Shop fantasyShop = new BookShop("Fantasy Books");
        shops.add(amazon);
        shops.add(bookStore);
        shops.add(bookSellers);
        shops.add(rareBooks);
        shops.add(fantasyShop);

        amazon.addBooks(loadBooks("src/main/resources/amazonbooks.json"));
        bookStore.addBooks(loadBooks("src/main/resources/amazonbooks.json"));
        bookSellers.addBooks(loadBooks("src/main/resources/booksellerbooks.json"));
        rareBooks.addBooks(loadBooks("src/main/resources/rarebooks.json"));
        fantasyShop.addBooks(loadBooks("src/main/resources/fantasybooks.json"));

        return shops;
    }

    private static List<Book> loadBooks(String source) {
        List<Book> books = new LinkedList<>();

        try {
            books = DataService.getBooksFrom(source);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return books;
    }

    private static User<Book> createUser(Scanner scanner) {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        User<Book> customer = new Customer(name);

        System.out.println("Enter your account balance: ");
        double cash = scanner.nextDouble();
        scanner.nextLine();
        customer.setCash(cash);

        System.out.println("Welcome, " + customer.getName());
        System.out.println(HEADER_LINE);

        return customer;
    }

    private static void showSelection(String[] interactions) {
        System.out.println(ACTION_SEPARATOR_LINE);
        for (int i = 0; i < interactions.length; i++) {
            System.out.println(String.format(INTERACTION_FORMAT, i, interactions[i]));
        }

        System.out.println(ACTION_SEPARATOR_LINE);
    }

    private static int userChoice(Scanner scanner, int from, int to) {
        int choice = scanner.nextInt();
        scanner.nextLine();

        while (choice > to || choice < from) {
            System.out.println(choice + " - Unknown command. Please try again");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        return choice;
    }

    private static void interactWithShops(List<Shop> shops, Scanner scanner, User<Book> customer) {
        Shop shop = selectShop(shops, scanner);

        System.out.println(HEADER_LINE);
        System.out.println(String.format("Welcome to %s %nSelect action: ", shop.getName()));

        int shopInteractionNumber;
        do {
            showSelection(shopInteractions);
            shopInteractionNumber = userChoice(scanner, 0, shopInteractions.length);
            if (shopInteractionNumber == 1) {
                showAllBooks(shop);
            } else if (shopInteractionNumber == 2) {
                filterByGenre(scanner, shop);
            } else if (shopInteractionNumber == 3) {
                buy(scanner, customer, shop);
            } else if (shopInteractionNumber == 4) {
                compareShops(shop, shops, scanner);
            } else if (shopInteractionNumber == 5 && customer.getIsOwner()) {
                addBookToShop(shop, scanner);
            } else {
                System.out.println("Access denied");
            }
        } while (shopInteractionNumber != EXIT_CODE);

    }

    private static Shop selectShop(List<Shop> shops, Scanner scanner) {
        System.out.println(ACTION_SEPARATOR_LINE);
        System.out.println("Enter the number of the shop");
        for (int i = 1; i <= shops.size(); i++) {
            System.out.println(String.format(INTERACTION_FORMAT, i, shops.get(i - 1).getName()));
        }

        int shopNumber = userChoice(scanner, 1, shops.size());

        return shops.get(shopNumber - 1);
    }
    
    private static void showAllBooks(Shop shop) {
        System.out.println(shop.getName() + " has following books:");
        shop.showAllBooksWithoutDuplicates();
    }

    private static void filterByGenre(Scanner scanner, Shop shop) {
        Genre genre = selectGenre(scanner);

        System.out.println(shop.getName() + " has following books:");
        shop.showBooksFilteredBy(genre);
    }

    private static Genre selectGenre(Scanner scanner) {
        System.out.println("Select genre: ");
        showSelection(genres);
        int genreNumber = userChoice(scanner, 0, genres.length);
        return Genre.valueOf(genreNumber);
    }

    private static void buy(Scanner scanner, User<Book> customer, Shop shop) {
        System.out.println("Enter the title of the book: ");
        String title = scanner.nextLine();
        customer.buyItemByShop(title, shop);
        System.out.println(title + " was bought");
    }

    private static void compareShops(Shop shop, List<Shop> shops, Scanner scanner) {
        System.out.println(String.format("You are in %s. What other shop would you compate to? ",
                shop.getName()));
        
        Shop otherShop = selectShop(shops, scanner);
        boolean isEqual = shop.compareBooksTo(otherShop);
        if (isEqual) {
            System.out.println("Books in both shops are equal");
        } else {
            System.out.println(String.format("There are different books in %s and %s",
                    shop.getName(), otherShop.getName()));
        }
    }

    private static void addBookToShop(Shop shop, Scanner scanner) {
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        
        System.out.println("Enter pages:");
        int pages = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Enter price:");
        double price = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.println("Enter ISBN:");
        String isbn = scanner.nextLine();
        
        Genre genre = selectGenre(scanner);

        Book book = Book.builder().withTitle(title).withPrice(price).withPages(pages)
                .withGenre(genre).withIsbn(isbn).build();
        shop.addNewBook(book);
        System.out.println("Book was successfully added");
    }

}
