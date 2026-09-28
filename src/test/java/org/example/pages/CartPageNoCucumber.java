package org.example.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CartPageNoCucumber extends BasePage {

    private final SelenideElement pageTitle = $(".title");
    private final ElementsCollection cartItems = $$(".cart_item");
    private final ElementsCollection itemNames = $$(".inventory_item_name");
    private final ElementsCollection removeButtons = $$("[data-test^='remove']");
    private final SelenideElement checkoutButton = $("#checkout");
    private final SelenideElement cartBadge = $(".shopping_cart_badge");
    private final SelenideElement continueShoppingButton = $("#continue-shopping");

    public CartPageNoCucumber() {
        isPageLoaded();
    }

    @Override
    public boolean isPageLoaded() {
        pageTitle.shouldBe(visible).shouldHave(text("Your Cart"));
        return true;
    }

    public CartPageNoCucumber verifyCartItemsCount(int expectedCount) {
        cartItems.shouldHave(size(expectedCount));
        return this;
    }

    public CartPageNoCucumber verifyProductInCart(String productName) {
        itemNames.findBy(text(productName)).shouldBe(visible);
        return this;
    }

    public CartPageNoCucumber removeItem(int index) {
        removeButtons.get(index).shouldBe(visible).click();
        return this;
    }

    public CartPageNoCucumber removeItem(String productName) {
        String productId = productName.toLowerCase().replace(" ", "-");
        $("[data-test='remove-" + productId + "']")
                .shouldBe(visible)
                .click();
        return this;
    }

    public CheckoutPageNoCucumber proceedToCheckout() {
        checkoutButton.shouldBe(visible).click();
        return new CheckoutPageNoCucumber();
    }

    public ProductsPageNoCucumber continueShopping() {
        continueShoppingButton.shouldBe(visible).click();
        return new ProductsPageNoCucumber();
    }

    public CartPageNoCucumber verifyEmptyCart() {
        cartItems.shouldHave(size(0));
        cartBadge.shouldNotBe(visible);
        return this;
    }
}