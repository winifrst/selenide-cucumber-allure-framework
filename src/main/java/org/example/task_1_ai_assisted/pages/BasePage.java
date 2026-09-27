package org.example.task_1_ai_assisted.pages;

import static com.codeborne.selenide.Selenide.open;

/**
 * Базовый класс для всех Page Object страниц.
 * Содержит общие методы для работы со страницами.
 */
public abstract class BasePage {

    protected static final String BASE_URL = "https://www.saucedemo.com";

    /**
     * Открывает страницу по указанному URL
     * @param url относительный или полный URL
     */
    public void openPage(String url) {
        open(url);
    }

    /**
     * Проверяет, загружена ли страница
     * @return true если страница загружена
     */
    public abstract boolean isPageLoaded();

    /**
     * Открывает страницу и ожидает её загрузки
     * @param url URL страницы
     * @return текущий объект страницы
     */
    public BasePage openAndWait(String url) {
        openPage(url);
        isPageLoaded();
        return this;
    }
}