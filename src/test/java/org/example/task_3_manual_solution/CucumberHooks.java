package org.example.task_3_manual_solution;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class CucumberHooks {

    @Before
    @Step("Настройка браузера перед тестом")
    public void setUp() {
        // Читаем параметры из gradle.properties
        String browser = System.getProperty("browser", "chrome");
        String headless = System.getProperty("headless", "false");
        String browserSize = System.getProperty("browserSize", "1600x900");
        String timeoutStr = System.getProperty("timeout", "10000");
        String baseUrl = System.getProperty("baseUrl", "https://www.saucedemo.com");

        long timeout = Long.parseLong(timeoutStr);

        // Применяем параметры
        Configuration.browser = browser;
        Configuration.headless = Boolean.parseBoolean(headless);
        Configuration.browserSize = browserSize;
        Configuration.timeout = timeout;
        Configuration.baseUrl = baseUrl;

        // Настройки для Chrome
        setupChromeOptions();

        System.out.println("========================================");
        System.out.println("Selenide Configuration:");
        System.out.println("  Browser:    " + Configuration.browser);
        System.out.println("  Headless:   " + Configuration.headless);
        System.out.println("  Size:       " + Configuration.browserSize);
        System.out.println("  Timeout:    " + Configuration.timeout + "ms");
        System.out.println("  Base URL:   " + Configuration.baseUrl);
        System.out.println("========================================");
    }

    private void setupChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordImport");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-features=PasswordProtectionService");
        options.addArguments("--disable-password-manager-reauthentication");
        options.addArguments("--disable-credential-manager");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("profile.password_manager_automatic_sign_in", false);
        prefs.put("profile.password_manager_check_public_suffix", false);

        options.setExperimentalOption("prefs", prefs);

        if (Configuration.headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=" + Configuration.browserSize);
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        Configuration.browserCapabilities = options;
    }

    @After
    @Step("Очистка состояния после теста")
    public void tearDown() {
        try {
            byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
            if (screenshot != null) {
                Allure.addAttachment("Screenshot on failure", "image/png",
                        new ByteArrayInputStream(screenshot), "png");
            }
        } catch (Exception e) {
            System.err.println("Не удалось сделать скриншот: " + e.getMessage());
        }

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.executeJavaScript("window.sessionStorage.clear();");
    }
}