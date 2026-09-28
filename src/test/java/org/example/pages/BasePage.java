package org.example.pages;

import static com.codeborne.selenide.Selenide.open;

public abstract class BasePage {

    protected static final String BASE_URL = "https://www.saucedemo.com";

    public void openPage(String url) {
        open(url);  // Теперь используется статический импорт
    }

    public abstract boolean isPageLoaded();
}