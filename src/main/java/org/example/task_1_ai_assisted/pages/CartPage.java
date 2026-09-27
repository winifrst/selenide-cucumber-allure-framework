package org.example.task_1_ai_assisted.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Page Object для страницы корзины
 * Содержит элементы и методы для работы с корзиной товаров
 */
public class CartPage extends BasePage {

    // Основные элементы
    private final SelenideElement pageTitle = $(".title");
    private final SelenideElement cartBadge = $(".shopping_cart_badge");
    private final SelenideElement checkoutButton = $("#checkout");
    private final SelenideElement continueShoppingButton = $("#continue-shopping");

    // Коллекции элементов корзины
    private final ElementsCollection cartItems = $$(".cart_item");
    private final ElementsCollection itemNames = $$(".inventory_item_name");
    private final ElementsCollection itemPrices = $$(".inventory_item_price");
    private final ElementsCollection removeButtons = $$("[data-test^='remove']");

    /**
     * Конструктор - проверяет загрузку страницы корзины
     */
    public CartPage() {
        isPageLoaded();
    }

    @Override
    public boolean isPageLoaded() {
        pageTitle.shouldBe(visible).shouldHave(text("Your Cart"));
        return true;
    }

    /**
     * Проверяет количество товаров в корзине
     * @param expectedCount ожидаемое количество
     * @return текущий объект CartPage
     */
    @Step("Проверить количество товаров в корзине: {expectedCount}")
    public CartPage verifyCartItemsCount(int expectedCount) {
        cartItems.shouldHave(size(expectedCount));
        return this;
    }

    /**
     * Проверяет наличие товара в корзине
     * @param productName название товара
     * @return текущий объект CartPage
     */
    @Step("Проверить наличие товара '{productName}' в корзине")
    public CartPage verifyProductInCart(String productName) {
        itemNames.findBy(text(productName)).shouldBe(visible);
        return this;
    }

    /**
     * Удаляет товар из корзины по индексу
     * @param index индекс товара
     * @return текущий объект CartPage
     */
    @Step("Удалить товар с индексом {index} из корзины")
    public CartPage removeItem(int index) {
        removeButtons.get(index).shouldBe(visible).click();
        return this;
    }

    /**
     * Удаляет товар из корзины по названию
     * @param productName название товара
     * @return текущий объект CartPage
     */
    @Step("Удалить товар '{productName}' из корзины")
    public CartPage removeItem(String productName) {
        String productId = productName.toLowerCase()
                .replace(" ", "-")
                .replace("'", "");
        $("[data-test='remove-" + productId + "']")
                .shouldBe(visible)
                .click();
        return this;
    }

    /**
     * Переходит к оформлению заказа
     * @return объект CheckoutPage
     */
    @Step("Перейти к оформлению заказа")
    public CheckoutPage proceedToCheckout() {
        checkoutButton.shouldBe(visible).click();
        return new CheckoutPage();
    }

    /**
     * Возвращается к покупкам
     * @return объект ProductsPage
     */
    @Step("Продолжить покупки")
    public ProductsPage continueShopping() {
        continueShoppingButton.shouldBe(visible).click();
        return new ProductsPage();
    }

    /**
     * Проверяет, что корзина пуста
     * @return текущий объект CartPage
     */
    @Step("Проверить, что корзина пуста")
    public CartPage verifyEmptyCart() {
        cartItems.shouldHave(size(0));
        cartBadge.shouldNotBe(visible);
        return this;
    }

    /**
     * Получает название товара по индексу
     * @param index индекс товара
     * @return название товара
     */
    @Step("Получить название товара из корзины по индексу: {index}")
    public String getCartItemName(int index) {
        return itemNames.get(index).shouldBe(visible).getText();
    }
}