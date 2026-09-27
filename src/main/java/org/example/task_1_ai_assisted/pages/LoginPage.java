package org.example.task_1_ai_assisted.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object для страницы авторизации
 * Содержит элементы и методы для взаимодействия с формой логина
 */
public class LoginPage extends BasePage {

    // Локаторы элементов страницы
    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    private final SelenideElement loginLogo = $(".login_logo");
    private final SelenideElement errorMessage = $("[data-test='error']");
//    private final SelenideElement botImage = $(".bot_column");

    /**
     * Конструктор - открывает страницу логина и проверяет её загрузку
     */
    public LoginPage() {
        openPage("/");
        isPageLoaded();
    }

    @Override
    public boolean isPageLoaded() {
        loginLogo.shouldBe(visible);
//        botImage.shouldBe(visible);
        usernameInput.shouldBe(visible);
        passwordInput.shouldBe(visible);
        loginButton.shouldBe(visible);
        return true;
    }

    /**
     * Вводит логин в поле username
     * @param username логин пользователя
     * @return текущий объект LoginPage для цепочки вызовов
     */
    @Step("Ввести логин: {username}")
    public LoginPage enterUsername(String username) {
        usernameInput.shouldBe(visible).clear();
        usernameInput.setValue(username);  // Исправлено: setValue() вызывается отдельно
        return this;
    }

    /**
     * Вводит пароль в поле password
     * @param password пароль пользователя
     * @return текущий объект LoginPage для цепочки вызовов
     */
    @Step("Ввести пароль: {password}")
    public LoginPage enterPassword(String password) {
        passwordInput.shouldBe(visible).clear();
        passwordInput.setValue(password);  // Исправлено: setValue() вызывается отдельно
        return this;
    }

    /**
     * Нажимает кнопку Login
     * @return текущий объект LoginPage для цепочки вызовов
     */
    @Step("Нажать кнопку Login")
    public LoginPage clickLoginButton() {
        loginButton.shouldBe(visible).click();
        return this;
    }

    /**
     * Выполняет вход с указанными данными
     * @param username логин
     * @param password пароль
     * @return объект ProductsPage после успешного входа
     */
    @Step("Выполнить вход с логином: {username} и паролем: {password}")
    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new ProductsPage();
    }

    /**
     * Выполняет вход с неверными данными
     * @param username логин
     * @param password пароль
     * @return текущий объект LoginPage для проверки ошибки
     */
    @Step("Выполнить вход с неверными данными: {username}, {password}")
    public LoginPage loginWithInvalidCredentials(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return this;
    }

    /**
     * Проверяет сообщение об ошибке
     * @param expectedMessage ожидаемое сообщение
     * @return текущий объект LoginPage для цепочки вызовов
     */
    @Step("Проверить сообщение об ошибке: {expectedMessage}")
    public LoginPage verifyErrorMessage(String expectedMessage) {
        errorMessage.shouldBe(visible)
                .shouldHave(text(expectedMessage));
        return this;
    }

    /**
     * Проверяет, что сообщение об ошибке отображается
     * @return текущий объект LoginPage
     */
    @Step("Проверить наличие сообщения об ошибке")
    public LoginPage verifyErrorIsDisplayed() {
        errorMessage.shouldBe(visible);
        return this;
    }
}