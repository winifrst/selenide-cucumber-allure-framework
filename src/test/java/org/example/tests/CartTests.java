package org.example.tests;

import org.example.pages.CartPageNoCucumber;
import org.example.pages.LoginPageNoCucumber;
import org.example.pages.ProductsPageNoCucumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты корзины")
public class CartTests extends BaseTest {

    @Test
    @DisplayName("Добавление товара в корзину")
    void addProductToCartTest() {
        LoginPageNoCucumber loginPage = new LoginPageNoCucumber();
        ProductsPageNoCucumber productsPage = loginPage.login("standard_user", "secret_sauce");

        String productName = productsPage.getProductName(0);
        productsPage.addProductToCart(0)
                .verifyCartCount(1);

        CartPageNoCucumber cartPage = productsPage.goToCart();
        cartPage.verifyProductInCart(productName);
    }

    @Test
    @DisplayName("Добавление нескольких товаров в корзину")
    void addMultipleProductsToCartTest() {
        LoginPageNoCucumber loginPage = new LoginPageNoCucumber();
        ProductsPageNoCucumber productsPage = loginPage.login("standard_user", "secret_sauce");

        productsPage.addProductToCart(0)
                .addProductToCart(1)
                .verifyCartCount(2);

        CartPageNoCucumber cartPage = productsPage.goToCart();
        cartPage.verifyCartItemsCount(2);
    }

    @Test
    @DisplayName("Удаление товара из корзины")
    void removeProductFromCartTest() {
        LoginPageNoCucumber loginPage = new LoginPageNoCucumber();
        ProductsPageNoCucumber productsPage = loginPage.login("standard_user", "secret_sauce");

        productsPage.addDefaultProductToCart();

        CartPageNoCucumber cartPage = productsPage.goToCart();
        cartPage.verifyCartItemsCount(1)
                .removeItem(0)
                .verifyEmptyCart();
    }
}