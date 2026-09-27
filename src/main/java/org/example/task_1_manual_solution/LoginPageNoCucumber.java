package org.example.task_1_manual_solution;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPageNoCucumber extends BasePage {

    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    private final SelenideElement logo = $(".login_logo");
    private final SelenideElement errorMessage = $("[data-test='error']");

    public LoginPageNoCucumber() {
        openPage("/");
        isPageLoaded(); // Проверка загрузки страницы
    }

    @Override
    public boolean isPageLoaded() {
        logo.shouldBe(visible);
        return true;
    }

    public ProductsPageNoCucumber login(String username, String password) {
        enterCredentials(username, password);
        loginButton.click();
        return new ProductsPageNoCucumber();
    }

    public LoginPageNoCucumber loginWithInvalidCredentials(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        return this;
    }

    public LoginPageNoCucumber clickLoginButton() {
        loginButton.click();
        return this;
    }

    public LoginPageNoCucumber verifyErrorMessage(String expectedMessage) {
        errorMessage.shouldBe(visible)
                .shouldHave(text(expectedMessage));
        return this;
    }

    public LoginPageNoCucumber enterCredentials(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        return this;
    }
}