package com.kozlova.bookshop;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.kozlova.bookshop.entity.Book;
import com.kozlova.bookshop.entity.BookShop;
import com.kozlova.bookshop.entity.Customer;
import com.kozlova.bookshop.entity.Genre;
import com.kozlova.bookshop.entity.Shop;
import com.kozlova.bookshop.entity.User;


public class ConsoleApplication {

    private static final String FORMAT = "%d: %s";
    private static final int EXIT_CODE = 0;

    public static void main(String[] args) {
        List<Shop> shops = createShops();

        try (Scanner scanner = new Scanner(System.in)) {
            User customer = createUser(scanner);

            List<String> appInteractions = new LinkedList<>();
            appInteractions.add("Exit");
            appInteractions.add("Go to shop as customer");
            appInteractions.add("Go to shop as owner");

            int actionNumber = 1;
            while (actionNumber != EXIT_CODE) {
                showInteractions(appInteractions);
                actionNumber = userChoice(scanner);

                if (actionNumber == 1) {
                    interactWithShops(shops, scanner, customer);
                } else if (actionNumber == 2) {
                    interactWithShops(shops, scanner, customer);
                } else if (actionNumber == EXIT_CODE) {
                    System.out.println("Bye!");
                    System.exit(0);
                } else {
                    System.out.println(String.format("Unknown input %d. Try again", actionNumber));
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }

    }

    private static User createUser(Scanner scanner) {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        User customer = new Customer(name);

        System.out.println("Enter your account balance: ");
        double cash = scanner.nextDouble();
        scanner.nextLine();
        customer.setCash(cash);
        return customer;
    }

    private static List<Shop> createShops() {
        List<Shop> shops = new LinkedList<>();
        Shop amazon = new BookShop("AmazonBooks");
        Shop bookStore = new BookShop("BookStore");
        Shop bookSellers = new BookShop("BookSellers");
        Shop rareBooks = new BookShop("Rare Books");
        Shop fantasyShop = new BookShop("Fantasy Books");
        amazon.addBooks(createBooks());
        shops.add(amazon);
        shops.add(bookStore);
        shops.add(bookSellers);
        shops.add(rareBooks);
        shops.add(fantasyShop);
        return shops;
    }

    private static List<Book> createBooks() {
        List<Book> books = new LinkedList<>();

        Book book1 = Book.builder().withTitle("Test1").withPrice(22.2).withPages(300).withGenre(Genre.ADVENTURE)
                .withIsbn("978-3608963762").build();
        Book book2 = Book.builder().withTitle("Test2").withPrice(22.2).withPages(300).withGenre(Genre.BIOGRAPHY)
                .withIsbn("978-3608963762").build();
        Book book3 = Book.builder().withTitle("Test3").withPrice(22.2).withPages(300).withGenre(Genre.COMIC)
                .withIsbn("978-3608963762").build();
        Book book4 = Book.builder().withTitle("Test4").withPrice(22.2).withPages(300).withGenre(Genre.FANTASY)
                .withIsbn("978-3608963762").build();
        Book book5 = Book.builder().withTitle("Test5").withPrice(22.2).withPages(300).withGenre(Genre.ADVENTURE)
                .withIsbn("978-3608963762").build();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        return books;
    }

    private static void interactWithShops(List<Shop> shops, Scanner scanner, User customer) {

        System.out.println("\nChoose the number of the shop you want to visit");
        showAllShops(shops);
        int shopNumber = userChoice(scanner);
        Shop choosenShop = chooseShop(shops, shopNumber - 1);

        System.out.println(String.format("\nYou have chosen %s", choosenShop.getName()));
        System.out.println("\nChoose the action: ");
        List<String> shopInteractions = new LinkedList<>();
        shopInteractions.add("Exit");
        shopInteractions.add("Show all books");
        shopInteractions.add("Select books by genre");
        shopInteractions.add("Buy a book");
        shopInteractions.add("Compare books with another shop");
        shopInteractions.add("Go to another shop");
        showInteractions(shopInteractions);
        int shopInteractionNumber = userChoice(scanner);

        if (shopInteractionNumber == 1) {
            choosenShop.showAllBooksWithoutDuplicates();
        } else if (shopInteractionNumber == 2) {
            filterByGenre(scanner, choosenShop);
        } else if (shopInteractionNumber == 3) {
            buy(scanner, customer, choosenShop);

        } else if (shopInteractionNumber == 4) {
            // compareShops();
        } else if (shopInteractionNumber == 5) {
            interactWithShops(shops, scanner, customer);

        } else if (shopInteractionNumber == EXIT_CODE) {
            System.out.println("Bye!");
            System.exit(0);
        } else {
            System.out.println("Invalid command");
        }
    }

    private static void filterByGenre(Scanner scanner, Shop choosenShop) {
        System.out.println("Choose the genre: ");

        List<String> genres = new LinkedList<>();
        genres.add(Genre.ADVENTURE.toString());
        genres.add(Genre.BIOGRAPHY.toString());
        genres.add(Genre.COMIC.toString());
        genres.add(Genre.FANTASY.toString());
        showInteractions(genres);
        int genreNumber = userChoice(scanner);
        System.out.println(Genre.valueOf(genreNumber));
        choosenShop.showBooksFilteredBy(Genre.valueOf(genreNumber));
    }

    private static void buy(Scanner scanner, User customer, Shop choosenShop) {
        System.out.println("Enter the title of the book: ");
        String title = scanner.nextLine();
        customer.buyBookByShop(title, choosenShop);
    }

    private static void showInteractions(List<String> interactions) {
        for (int i = 0; i < interactions.size(); i++) {
            System.out.println(String.format(FORMAT, i, interactions.get(i)));
        }
    }

    private static int userChoice(Scanner scanner) {
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private static Shop chooseShop(List<Shop> shops, int shopNumber) {
        return shops.get(shopNumber);
    }

    public static void showAllShops(List<Shop> shops) {
        for (int i = 0; i < shops.size(); i++) {
            System.out.println(String.format(FORMAT, i + 1, shops.get(i).getName()));
        }

    }

}
