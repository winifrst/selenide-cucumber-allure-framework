package org.example.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$;


public class ProductsPageNoCucumber extends BasePage {

    private final SelenideElement appLogo = $(".app_logo");
    private final SelenideElement pageTitle = $(".title");
    private final ElementsCollection inventoryItems = $$(".inventory_item");
    private final SelenideElement cartBadge = $(".shopping_cart_badge");
    private final SelenideElement cartLink = $(".shopping_cart_link");
    private final ElementsCollection productImages = $$(".inventory_item_img");
    private final ElementsCollection addToCartButtons = $$("[data-test^='add-to-cart']");
    private final ElementsCollection productNames = $$(".inventory_item_name");

    public ProductsPageNoCucumber() {
        isPageLoaded();
    }

    @Override
    public boolean isPageLoaded() {
        appLogo.shouldBe(visible).shouldHave(text("Swag Labs"));
        pageTitle.shouldBe(visible).shouldHave(text("Products"));
        inventoryItems.shouldHave(size(6));
        cartLink.shouldBe(visible);
        return true;
    }

    public String getProductName(int index) {
        return productNames.get(index).shouldBe(visible).getText();
    }

    public ProductsPageNoCucumber addProductToCart(int index) {
        addToCartButtons.get(index).shouldBe(visible).click();
        return this;
    }

    public ProductsPageNoCucumber addProductToCart(String productName) {
        String productId = productName.toLowerCase().replace(" ", "-");
        $("[data-test='add-to-cart-" + productId + "']")
                .shouldBe(visible)
                .click();
        return this;
    }

    public ProductsPageNoCucumber addDefaultProductToCart() {
        $("[data-test='add-to-cart-sauce-labs-backpack']")
                .shouldBe(visible)
                .click();
        return this;
    }

    public ProductsPageNoCucumber verifyCartCount(int expectedCount) {
        if (expectedCount > 0) {
            cartBadge.shouldBe(visible)
                    .shouldHave(text(String.valueOf(expectedCount)));
        } else {
            cartBadge.shouldNotBe(visible);
        }
        return this;
    }

    public CartPageNoCucumber goToCart() {
        cartLink.shouldBe(visible).click();
        return new CartPageNoCucumber();
    }

    public ProductsPageNoCucumber verifyAllImagesVisible() {
        productImages.forEach(img -> img.shouldBe(visible));
        return this;
    }
}