package org.example.cucumber;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.example.pages.CartPageNoCucumber;
import org.example.pages.CheckoutPageNoCucumber;
import org.example.pages.LoginPageNoCucumber;
import org.example.pages.ProductsPageNoCucumber;

public class CommonSteps {

    private LoginPageNoCucumber loginPage;
    private ProductsPageNoCucumber productsPage;
    private CartPageNoCucumber cartPage;
    private CheckoutPageNoCucumber checkoutPage;

    // ===== Шаги для страницы логина =====
    @Дано("пользователь открыл страницу логина")
    public void openLoginPage() {
        loginPage = new LoginPageNoCucumber();
    }

    @Когда("пользователь вводит логин {string} и пароль {string}")
    public void enterCredentials(String username, String password) {
        loginPage.enterCredentials(username, password);
    }

    @Когда("нажимает кнопку {string}")
    public void clickButton(String buttonName) {
        switch (buttonName) {
            case "Login":
                loginPage.clickLoginButton();
                break;
            case "Checkout":
                if (cartPage == null) {
                    cartPage = productsPage.goToCart();
                }
                cartPage.proceedToCheckout();
                checkoutPage = new CheckoutPageNoCucumber();
                break;
            case "Continue":
                if (checkoutPage == null) {
                    checkoutPage = new CheckoutPageNoCucumber();
                }
                checkoutPage.continueCheckout();
                break;
            case "Finish":
                if (checkoutPage == null) {
                    checkoutPage = new CheckoutPageNoCucumber();
                }
                checkoutPage.finishOrder();
                break;
            default:
                throw new IllegalArgumentException("Unknown button: " + buttonName);
        }
    }

    @Тогда("пользователь видит страницу товаров")
    public void verifyProductsPage() {
        productsPage = new ProductsPageNoCucumber();
        productsPage.verifyAllImagesVisible();
    }

    @Тогда("пользователь видит сообщение об ошибке {string}")
    public void verifyErrorMessage(String expectedMessage) {
        if (loginPage != null) {
            loginPage.verifyErrorMessage(expectedMessage);
        } else if (checkoutPage != null) {
            checkoutPage.verifyError(expectedMessage);
        }
    }

    // ===== Шаги для авторизации =====
    @Дано("пользователь авторизован как {string}")
    public void userIsAuthorized(String username) {
        loginPage = new LoginPageNoCucumber();
        productsPage = loginPage.login(username, "secret_sauce");
        // После авторизации сбрасываем корзину, чтобы начать с чистого состояния
        productsPage.verifyCartCount(0);
    }

    // ===== Шаги для корзины =====
    @Дано("пользователь добавил товар в корзину")
    public void userAddedProductToCart() {
        // Проверяем, что мы на странице товаров
        if (productsPage == null) {
            // Если нет - авторизуемся
            userIsAuthorized("standard_user");
        }
        // Добавляем товар в корзину
        productsPage.addDefaultProductToCart();
        // Проверяем, что товар добавился
        productsPage.verifyCartCount(1);
    }

    @Когда("пользователь добавляет первый товар в корзину")
    public void addFirstProductToCart() {
        if (productsPage == null) {
            userIsAuthorized("standard_user");
        }
        productsPage.addProductToCart(0);
    }

    @Когда("пользователь добавляет второй товар в корзину")
    public void addSecondProductToCart() {
        if (productsPage == null) {
            userIsAuthorized("standard_user");
        }
        productsPage.addProductToCart(1);
    }

    @Когда("пользователь переходит в корзину")
    public void goToCart() {
        if (productsPage == null) {
            userIsAuthorized("standard_user");
        }
        cartPage = productsPage.goToCart();
    }

    @Когда("заполняет данные: имя {string}, фамилия {string}, индекс {string}")
    public void fillCheckoutData(String firstName, String lastName, String postalCode) {
        if (checkoutPage == null) {
            checkoutPage = new CheckoutPageNoCucumber();
        }
        checkoutPage.fillCheckoutInfo(firstName, lastName, postalCode);
    }

    // ===== Шаги для проверок =====
    @Тогда("счетчик корзины показывает {int}")
    public void verifyCartCount(int expectedCount) {
        if (productsPage == null) {
            userIsAuthorized("standard_user");
        }
        productsPage.verifyCartCount(expectedCount);
    }

    @Тогда("заказ успешно оформлен")
    public void verifyOrderComplete() {
        if (checkoutPage == null) {
            checkoutPage = new CheckoutPageNoCucumber();
        }
        checkoutPage.verifyOrderComplete();
    }

    @Тогда("пользователь видит ошибку {string}")
    public void verifyCheckoutError(String expectedMessage) {
        if (checkoutPage != null) {
            checkoutPage.verifyError(expectedMessage);
        } else {
            loginPage.verifyErrorMessage(expectedMessage);
        }
    }
}