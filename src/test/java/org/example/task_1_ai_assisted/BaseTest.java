package org.example.task_1_ai_assisted;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

/**
 * Базовый класс для всех тестов
 * Содержит настройки Selenide и методы очистки состояния
 */
public abstract class BaseTest {

    @BeforeAll
    static void setUpAll() {
        // Читаем параметры из gradle.properties
        String browser = System.getProperty("browser", "chrome");
        String headless = System.getProperty("headless", "false");
        String browserSize = System.getProperty("browserSize", "1600x900");
        String baseUrl = System.getProperty("baseUrl", "https://www.saucedemo.com");

        // Применяем параметры к базовой конфигурации
        Configuration.browser = browser;
        Configuration.headless = Boolean.parseBoolean(headless);
        Configuration.browserSize = browserSize;
        Configuration.baseUrl = baseUrl;

        // Настраиваем конкретный браузер с его опциями
        setupBrowserOptions(browser);

        // Логируем финальную конфигурацию
        logConfiguration();
    }

    private static void setupBrowserOptions(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                setupChrome();
                break;
            case "firefox":
                setupFirefox();
                break;
            case "edge":
                setupEdge();
                break;
            default:
                System.out.println("⚠️ Browser '" + browser + "' будет использовать настройки по умолчанию");
        }
    }

    private static void setupChrome() {
        ChromeOptions options = new ChromeOptions();

        // Отключаем сохранение паролей и уведомления
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordImport");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-features=PasswordProtectionService");
        options.addArguments("--disable-password-manager-reauthentication");
        options.addArguments("--disable-credential-manager");

        // Настройки профиля - отключаем менеджер паролей
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("profile.password_manager_automatic_sign_in", false);
        prefs.put("profile.password_manager_check_public_suffix", false);

        options.setExperimentalOption("prefs", prefs);

        // Если headless режим, добавляем дополнительные аргументы
        if (Configuration.headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=" + Configuration.browserSize);
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        Configuration.browserCapabilities = options;
    }

    private static void setupFirefox() {
        FirefoxOptions options = new FirefoxOptions();

        // Отключаем уведомления
        options.addPreference("dom.webnotifications.enabled", false);

        // Отключаем сохранение паролей
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("signon.autofillForms", false);
        options.addPreference("signon.storeWhenAutocompleteOff", false);

        if (Configuration.headless) {
            options.addArguments("--headless");
            options.addArguments("--window-size=" + Configuration.browserSize);
        }

        Configuration.browserCapabilities = options;
    }

    private static void setupEdge() {
        EdgeOptions options = new EdgeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-save-password-bubble");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        if (Configuration.headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=" + Configuration.browserSize);
        }

        Configuration.browserCapabilities = options;
    }

    private static void logConfiguration() {
        System.out.println("========================================");
        System.out.println("🚀 Selenide Configuration:");
        System.out.println("  Browser:    " + Configuration.browser);
        System.out.println("  Headless:   " + Configuration.headless);
        System.out.println("  Size:       " + Configuration.browserSize);
        System.out.println("  Base URL:   " + Configuration.baseUrl);
        System.out.println("========================================");
    }

    @AfterAll
    static void tearDownAll() {
        closeWebDriver();
    }

    @AfterEach
    void tearDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        executeJavaScript("window.sessionStorage.clear();");
    }
}