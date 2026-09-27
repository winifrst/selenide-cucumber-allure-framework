package org.example.task_1_ai_assisted.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object для страницы оформления заказа
 * Содержит элементы и методы для работы с checkout процессом
 */
public class CheckoutPage extends BasePage {

    // Элементы страницы оформления
    private final SelenideElement pageTitle = $(".title");
    private final SelenideElement firstNameInput = $("#first-name");
    private final SelenideElement lastNameInput = $("#last-name");
    private final SelenideElement postalCodeInput = $("#postal-code");
    private final SelenideElement continueButton = $("#continue");
    private final SelenideElement cancelButton = $("#cancel");
    private final SelenideElement errorMessage = $("[data-test='error']");

    // Элементы страницы обзора заказа
    private final SelenideElement finishButton = $("#finish");
    private final SelenideElement completeHeader = $(".complete-header");
    private final SelenideElement completeText = $(".complete-text");

    /**
     * Конструктор - проверяет загрузку страницы оформления
     */
    public CheckoutPage() {
        isPageLoaded();
    }

    @Override
    public boolean isPageLoaded() {
        pageTitle.shouldBe(visible)
                .shouldHave(text("Checkout: Your Information"));
        firstNameInput.shouldBe(visible);
        lastNameInput.shouldBe(visible);
        postalCodeInput.shouldBe(visible);
        continueButton.shouldBe(visible);
        return true;
    }

    /**
     * Заполняет информацию о покупателе
     * @param firstName имя
     * @param lastName фамилия
     * @param postalCode почтовый индекс
     * @return текущий объект CheckoutPage
     */
    @Step("Заполнить информацию: имя '{firstName}', фамилия '{lastName}', индекс '{postalCode}'")
    public CheckoutPage fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        firstNameInput.shouldBe(visible).setValue(firstName);
        lastNameInput.shouldBe(visible).setValue(lastName);
        postalCodeInput.shouldBe(visible).setValue(postalCode);
        return this;
    }

    /**
     * Нажимает кнопку Continue
     * @return текущий объект CheckoutPage для продолжения цепочки
     */
    @Step("Нажать кнопку Continue")
    public CheckoutPage continueCheckout() {
        continueButton.shouldBe(visible).click();
        return this;
    }

    /**
     * Проверяет, что открыта страница обзора заказа
     * @return текущий объект CheckoutPage
     */
    @Step("Проверить загрузку страницы обзора заказа")
    public CheckoutPage verifyOverviewPageLoaded() {
        pageTitle.shouldBe(visible)
                .shouldHave(text("Checkout: Overview"));
        return this;
    }

    /**
     * Завершает оформление заказа
     * @return текущий объект CheckoutPage
     */
    @Step("Завершить оформление заказа")
    public CheckoutPage finishOrder() {
        finishButton.shouldBe(visible).click();
        return this;
    }

    /**
     * Проверяет успешное завершение заказа
     * @return текущий объект CheckoutPage
     */
    @Step("Проверить успешное оформление заказа")
    public CheckoutPage verifyOrderComplete() {
        completeHeader.shouldBe(visible)
                .shouldHave(text("Thank you for your order!"));
        completeText.shouldBe(visible);
        return this;
    }

    /**
     * Проверяет сообщение об ошибке
     * @param expectedMessage ожидаемое сообщение
     * @return текущий объект CheckoutPage
     */
    @Step("Проверить сообщение об ошибке: {expectedMessage}")
    public CheckoutPage verifyError(String expectedMessage) {
        errorMessage.shouldBe(visible)
                .shouldHave(text(expectedMessage));
        return this;
    }

    /**
     * Выполняет полный процесс оформления заказа
     * @param firstName имя
     * @param lastName фамилия
     * @param postalCode почтовый индекс
     * @return текущий объект CheckoutPage
     */
    @Step("Оформить заказ полностью: {firstName} {lastName}, {postalCode}")
    public CheckoutPage completeOrder(String firstName, String lastName, String postalCode) {
        return fillCheckoutInfo(firstName, lastName, postalCode)
                .continueCheckout()
                .verifyOverviewPageLoaded()
                .finishOrder()
                .verifyOrderComplete();
    }

    /**
     * Отменяет оформление заказа
     * @return объект CartPage
     */
    @Step("Отменить оформление заказа")
    public CartPage cancelCheckout() {
        cancelButton.shouldBe(visible).click();
        return new CartPage();
    }
}