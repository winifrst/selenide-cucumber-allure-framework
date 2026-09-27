package org.example.task_1_ai_assisted.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Page Object для страницы товаров
 * Содержит элементы и методы для работы с каталогом товаров
 */
public class ProductsPage extends BasePage {

    // Основные элементы страницы
    private final SelenideElement appLogo = $(".app_logo");
    private final SelenideElement pageTitle = $(".title");
    private final SelenideElement cartBadge = $(".shopping_cart_badge");
    private final SelenideElement cartLink = $(".shopping_cart_link");
    private final SelenideElement burgerMenu = $("#react-burger-menu-btn");

    // Коллекции элементов
    private final ElementsCollection inventoryItems = $$(".inventory_item");
    private final ElementsCollection productNames = $$(".inventory_item_name");
    private final ElementsCollection productPrices = $$(".inventory_item_price");
    private final ElementsCollection addToCartButtons = $$("[data-test^='add-to-cart']");
    private final ElementsCollection productImages = $$(".inventory_item_img");

    /**
     * Конструктор - проверяет загрузку страницы товаров
     */
    public ProductsPage() {
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

    /**
     * Получает название товара по индексу
     * @param index индекс товара (начиная с 0)
     * @return название товара
     */
    @Step("Получить название товара по индексу: {index}")
    public String getProductName(int index) {
        return productNames.get(index).shouldBe(visible).getText();
    }

    /**
     * Получает цену товара по индексу
     * @param index индекс товара
     * @return цена товара
     */
    @Step("Получить цену товара по индексу: {index}")
    public String getProductPrice(int index) {
        return productPrices.get(index).shouldBe(visible).getText();
    }

    /**
     * Добавляет товар в корзину по индексу
     * @param index индекс товара
     * @return текущий объект ProductsPage
     */
    @Step("Добавить товар с индексом {index} в корзину")
    public ProductsPage addProductToCart(int index) {
        addToCartButtons.get(index).shouldBe(visible).click();
        return this;
    }

    /**
     * Добавляет товар в корзину по названию
     * @param productName название товара
     * @return текущий объект ProductsPage
     */
    @Step("Добавить товар '{productName}' в корзину")
    public ProductsPage addProductToCart(String productName) {
        String productId = productName.toLowerCase()
                .replace(" ", "-")
                .replace("'", "");
        $("[data-test='add-to-cart-" + productId + "']")
                .shouldBe(visible)
                .click();
        return this;
    }

    /**
     * Добавляет дефолтный товар (Sauce Labs Backpack) в корзину
     * @return текущий объект ProductsPage
     */
    @Step("Добавить стандартный товар в корзину")
    public ProductsPage addDefaultProductToCart() {
        $("[data-test='add-to-cart-sauce-labs-backpack']")
                .shouldBe(visible)
                .click();
        return this;
    }

    /**
     * Проверяет количество товаров в корзине
     * @param expectedCount ожидаемое количество
     * @return текущий объект ProductsPage
     */
    @Step("Проверить счетчик корзины: {expectedCount}")
    public ProductsPage verifyCartCount(int expectedCount) {
        if (expectedCount > 0) {
            cartBadge.shouldBe(visible)
                    .shouldHave(text(String.valueOf(expectedCount)));
        } else {
            cartBadge.shouldNotBe(visible);
        }
        return this;
    }

    /**
     * Переходит в корзину
     * @return объект CartPage
     */
    @Step("Перейти в корзину")
    public CartPage goToCart() {
        cartLink.shouldBe(visible).click();
        return new CartPage();
    }

    /**
     * Проверяет, что все изображения товаров отображаются
     * @return текущий объект ProductsPage
     */
    @Step("Проверить отображение всех изображений товаров")
    public ProductsPage verifyAllImagesVisible() {
        productImages.forEach(img -> img.shouldBe(visible));
        return this;
    }

    /**
     * Проверяет количество товаров на странице
     * @param expectedCount ожидаемое количество
     * @return текущий объект ProductsPage
     */
    @Step("Проверить количество товаров: {expectedCount}")
    public ProductsPage verifyItemsCount(int expectedCount) {
        inventoryItems.shouldHave(size(expectedCount));
        return this;
    }

    /**
     * Открывает боковое меню
     * @return текущий объект ProductsPage
     */
    @Step("Открыть боковое меню")
    public ProductsPage openBurgerMenu() {
        burgerMenu.shouldBe(visible).click();
        return this;
    }
}