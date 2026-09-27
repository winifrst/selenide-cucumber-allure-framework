package org.example.task_1_manual_solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты оформления заказа")
public class CheckoutTests extends BaseTest {

    private ProductsPageNoCucumber productsPage;

    @BeforeEach
    void setup() {
        LoginPageNoCucumber loginPage = new LoginPageNoCucumber();
        productsPage = loginPage.login("standard_user", "secret_sauce");
        productsPage.addDefaultProductToCart();
    }

    @Test
    @DisplayName("Успешное оформление заказа")
    void successfulCheckoutTest() {
        CartPageNoCucumber cartPage = productsPage.goToCart();
        cartPage.verifyCartItemsCount(1);

        CheckoutPageNoCucumber checkoutPage = cartPage.proceedToCheckout();
        checkoutPage.completeOrder("John", "Doe", "12345");
    }

    @Test
    @DisplayName("Оформление заказа без заполнения имени")
    void checkoutWithoutFirstNameTest() {
        CartPageNoCucumber cartPage = productsPage.goToCart();
        CheckoutPageNoCucumber checkoutPage = cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutInfo("", "Doe", "12345")
                .continueCheckout()
                .verifyError("Error: First Name is required");
    }

    @Test
    @DisplayName("Оформление заказа без заполнения фамилии")
    void checkoutWithoutLastNameTest() {
        CartPageNoCucumber cartPage = productsPage.goToCart();
        CheckoutPageNoCucumber checkoutPage = cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutInfo("John", "", "12345")
                .continueCheckout()
                .verifyError("Error: Last Name is required");
    }

    @Test
    @DisplayName("Оформление заказа без заполнения почтового индекса")
    void checkoutWithoutPostalCodeTest() {
        CartPageNoCucumber cartPage = productsPage.goToCart();
        CheckoutPageNoCucumber checkoutPage = cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutInfo("John", "Doe", "")
                .continueCheckout()
                .verifyError("Error: Postal Code is required");
    }
}