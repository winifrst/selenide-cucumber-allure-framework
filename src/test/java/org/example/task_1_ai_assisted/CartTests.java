package org.example.task_1_ai_assisted;

import org.example.task_1_ai_assisted.pages.CartPage;
import org.example.task_1_ai_assisted.pages.LoginPage;
import org.example.task_1_ai_assisted.pages.ProductsPage;
import org.example.task_1_ai_assisted.utils.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.example.task_1_ai_assisted.utils.TestData.VALID_PASSWORD;
import static org.example.task_1_ai_assisted.utils.TestData.VALID_USERNAME;

/**
 * Тесты для проверки функциональности корзины
 */
@DisplayName("Тесты корзины")
public class CartTests extends BaseTest {

    @Test
    @DisplayName("Добавление товара в корзину")
    void addProductToCartTest() {
        // Arrange
        LoginPage loginPage = new LoginPage();
        ProductsPage productsPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);

        // Act
        String productName = productsPage.getProductName(0);
        productsPage.addProductToCart(0)
                .verifyCartCount(1);

        // Assert
        CartPage cartPage = productsPage.goToCart();
        cartPage.verifyProductInCart(productName);
    }

    @Test
    @DisplayName("Добавление нескольких товаров в корзину")
    void addMultipleProductsToCartTest() {
        // Arrange
        LoginPage loginPage = new LoginPage();
        ProductsPage productsPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);

        // Act
        productsPage.addProductToCart(0)
                .addProductToCart(1)
                .verifyCartCount(2);

        // Assert
        CartPage cartPage = productsPage.goToCart();
        cartPage.verifyCartItemsCount(2);
    }

    @Test
    @DisplayName("Удаление товара из корзины")
    void removeProductFromCartTest() {
        // Arrange
        LoginPage loginPage = new LoginPage();
        ProductsPage productsPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        productsPage.addDefaultProductToCart();

        // Act & Assert
        CartPage cartPage = productsPage.goToCart();
        cartPage.verifyCartItemsCount(1)
                .removeItem(0)
                .verifyEmptyCart();
    }

    @Test
    @DisplayName("Добавление товара по названию")
    void addProductByNameTest() {
        // Arrange
        LoginPage loginPage = new LoginPage();
        ProductsPage productsPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        String productName = "Sauce Labs Backpack";

        // Act
        productsPage.addProductToCart(productName)
                .verifyCartCount(1);

        // Assert
        CartPage cartPage = productsPage.goToCart();
        cartPage.verifyProductInCart(productName);
    }

    @Test
    @DisplayName("Продолжить покупки после просмотра корзины")
    void continueShoppingTest() {
        // Arrange
        LoginPage loginPage = new LoginPage();
        ProductsPage productsPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        productsPage.addDefaultProductToCart();

        // Act
        CartPage cartPage = productsPage.goToCart();
        ProductsPage newProductsPage = cartPage.continueShopping();

        // Assert
        newProductsPage.verifyAllImagesVisible();
        newProductsPage.verifyCartCount(1);
    }
}