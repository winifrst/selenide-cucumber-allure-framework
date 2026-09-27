package org.example.task_1_manual_solution;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты авторизации")
public class LoginTests extends BaseTest {

    @Test
    @DisplayName("Успешная авторизация с валидными данными")
    void successfulLoginTest() {
        LoginPageNoCucumber loginPage = new LoginPageNoCucumber();
        ProductsPageNoCucumber productsPage = loginPage.login("standard_user", "secret_sauce");

        productsPage.verifyAllImagesVisible();
        productsPage.verifyCartCount(0);
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    void loginWithInvalidPasswordTest() {
        LoginPageNoCucumber loginPage = new LoginPageNoCucumber();
        loginPage.loginWithInvalidCredentials("standard_user", "wrong_password")
                .verifyErrorMessage("Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    @DisplayName("Авторизация с пустыми полями")
    void loginWithEmptyFieldsTest() {
        LoginPageNoCucumber loginPage = new LoginPageNoCucumber();
        loginPage.clickLoginButton()
                .verifyErrorMessage("Epic sadface: Username is required");
    }
}