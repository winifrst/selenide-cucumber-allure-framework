package org.example.task_1_manual_solution;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CheckoutPageNoCucumber extends BasePage {

    private final SelenideElement pageTitle = $(".title");
    private final SelenideElement firstNameInput = $("#first-name");
    private final SelenideElement lastNameInput = $("#last-name");
    private final SelenideElement postalCodeInput = $("#postal-code");
    private final SelenideElement continueButton = $("#continue");
    private final SelenideElement finishButton = $("#finish");
    private final SelenideElement errorMessage = $("[data-test='error']");
    private final SelenideElement completeHeader = $(".complete-header");

    public CheckoutPageNoCucumber() {
        isPageLoaded();
    }

    @Override
    public boolean isPageLoaded() {
        pageTitle.shouldBe(visible)
                .shouldHave(text("Checkout: Your Information"));
        return true;
    }

    public CheckoutPageNoCucumber fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        postalCodeInput.setValue(postalCode);
        return this;
    }

    public CheckoutPageNoCucumber continueCheckout() {
        continueButton.shouldBe(visible).click();
        return this;
    }

    public CheckoutPageNoCucumber verifyOverviewPageLoaded() {
        pageTitle.shouldBe(visible)
                .shouldHave(text("Checkout: Overview"));
        return this;
    }

    public CheckoutPageNoCucumber finishOrder() {
        finishButton.shouldBe(visible).click();
        return this;
    }

    public CheckoutPageNoCucumber verifyOrderComplete() {
        completeHeader.shouldBe(visible)
                .shouldHave(text("Thank you for your order!"));
        return this;
    }

    public CheckoutPageNoCucumber verifyError(String expectedMessage) {
        errorMessage.shouldBe(visible)
                .shouldHave(text(expectedMessage));
        return this;
    }

    public CheckoutPageNoCucumber completeOrder(String firstName, String lastName, String postalCode) {
        fillCheckoutInfo(firstName, lastName, postalCode)
                .continueCheckout()
                .verifyOverviewPageLoaded()
                .finishOrder()
                .verifyOrderComplete();
        return this;
    }
}