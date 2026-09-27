package org.example.task_1_ai_assisted;

import org.example.task_1_ai_assisted.pages.LoginPage;
import org.example.task_1_ai_assisted.pages.ProductsPage;
import org.example.task_1_ai_assisted.utils.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.example.task_1_ai_assisted.utils.TestData.*;

/**
 * Тесты для проверки функциональности авторизации
 */
@DisplayName("Тесты авторизации")
public class LoginTests extends BaseTest {

    @Test
    @DisplayName("Успешная авторизация с валидными данными")
    void successfulLoginTest() {
        // Arrange
        LoginPage loginPage = new LoginPage();

        // Act
        ProductsPage productsPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);

        // Assert
        productsPage.verifyAllImagesVisible();
        productsPage.verifyCartCount(0);
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    void loginWithInvalidPasswordTest() {
        // Arrange
        LoginPage loginPage = new LoginPage();

        // Act
        loginPage.loginWithInvalidCredentials(VALID_USERNAME, INVALID_PASSWORD);

        // Assert
        loginPage.verifyErrorMessage(ERROR_INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Авторизация с пустыми полями")
    void loginWithEmptyFieldsTest() {
        // Arrange
        LoginPage loginPage = new LoginPage();

        // Act
        loginPage.clickLoginButton();

        // Assert
        loginPage.verifyErrorMessage(ERROR_USERNAME_REQUIRED);
    }

    @Test
    @DisplayName("Авторизация с пустым паролем")
    void loginWithEmptyPasswordTest() {
        // Arrange
        LoginPage loginPage = new LoginPage();

        // Act
        loginPage.enterUsername(VALID_USERNAME)
                .clickLoginButton();

        // Assert
        loginPage.verifyErrorMessage(ERROR_PASSWORD_REQUIRED);
    }
}