package com.kozlova.bookshop.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;    
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kozlova.bookshop.exception.InsufficientMoneyException;

@ExtendWith(MockitoExtension.class)
class CustomerTest {

    @Mock
    private Shop shop;

    private User<Book> customer = new Customer("Jack");

    @Test
    void buyBookByShopShouldAddNewBookToCollectionIfPurchaseWasSuccessful() {
        customer.setCash(23);
        String title = "Some book title";
        Book book = Book.builder().withTitle(title).withPrice(22.2).build();
        when(shop.getBookByTitle(title)).thenReturn(book);

        customer.buyItemByShop(title, shop);

        assertThat(customer.getCollection(), hasItem(book));
        verify(shop).getBookByTitle(title);
        verify(shop).sale(book);
        verifyNoMoreInteractions(shop);
    }

    @Test
    void buyBookByShopShouldThrowInsufficientMoneyExceptionIfCustomerHasNotEnoughCash() {
        customer.setCash(10);
        String title = "Some book title";
        Book book = Book.builder().withTitle(title).withPrice(20).build();
        when(shop.getBookByTitle(title)).thenReturn(book);

        InsufficientMoneyException thrown = assertThrows(InsufficientMoneyException.class,
                () -> customer.buyItemByShop(title, shop));

        assertThat(thrown.getMessage(),
                equalTo("Customer is not allowed to have a negative account balance"));
        assertThat(customer.getCollection(), not(hasItem(book)));
        verify(shop).getBookByTitle(title);
        verifyNoMoreInteractions(shop);
    }

}
