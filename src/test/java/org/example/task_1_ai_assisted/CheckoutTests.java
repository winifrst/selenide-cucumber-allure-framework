package org.example.task_1_ai_assisted;

import org.example.task_1_ai_assisted.pages.CartPage;
import org.example.task_1_ai_assisted.pages.CheckoutPage;
import org.example.task_1_ai_assisted.pages.LoginPage;
import org.example.task_1_ai_assisted.pages.ProductsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.example.task_1_ai_assisted.utils.TestData.*;

/**
 * Тесты для проверки функциональности оформления заказа
 */
@DisplayName("Тесты оформления заказа")
public class CheckoutTests extends BaseTest {

    private ProductsPage productsPage;

    @BeforeEach
    void setup() {
        LoginPage loginPage = new LoginPage();
        productsPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        productsPage.addDefaultProductToCart();
    }

    @Test
    @DisplayName("Успешное оформление заказа")
    void successfulCheckoutTest() {
        // Arrange
        CartPage cartPage = productsPage.goToCart();
        cartPage.verifyCartItemsCount(1);

        // Act
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        checkoutPage.completeOrder(FIRST_NAME, LAST_NAME, POSTAL_CODE);

        // Assert
        checkoutPage.verifyOrderComplete();
    }

    @Test
    @DisplayName("Оформление заказа без заполнения имени")
    void checkoutWithoutFirstNameTest() {
        // Arrange
        CartPage cartPage = productsPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Act
        checkoutPage.fillCheckoutInfo(FIRST_NAME_EMPTY, LAST_NAME, POSTAL_CODE)
                .continueCheckout();

        // Assert
        checkoutPage.verifyError(ERROR_FIRST_NAME_REQUIRED);
    }

    @Test
    @DisplayName("Оформление заказа без заполнения фамилии")
    void checkoutWithoutLastNameTest() {
        // Arrange
        CartPage cartPage = productsPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Act
        checkoutPage.fillCheckoutInfo(FIRST_NAME, LAST_NAME_EMPTY, POSTAL_CODE)
                .continueCheckout();

        // Assert
        checkoutPage.verifyError(ERROR_LAST_NAME_REQUIRED);
    }

    @Test
    @DisplayName("Оформление заказа без заполнения почтового индекса")
    void checkoutWithoutPostalCodeTest() {
        // Arrange
        CartPage cartPage = productsPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Act
        checkoutPage.fillCheckoutInfo(FIRST_NAME, LAST_NAME, POSTAL_CODE_EMPTY)
                .continueCheckout();

        // Assert
        checkoutPage.verifyError(ERROR_POSTAL_CODE_REQUIRED);
    }

    @Test
    @DisplayName("Отмена оформления заказа")
    void cancelCheckoutTest() {
        // Arrange
        CartPage cartPage = productsPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Act
        CartPage newCartPage = checkoutPage.cancelCheckout();

        // Assert
        newCartPage.verifyCartItemsCount(1);
        newCartPage.verifyProductInCart("Sauce Labs Backpack");
    }
}