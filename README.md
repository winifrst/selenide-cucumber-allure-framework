# SauceDemo UI Automation

Фреймворк для автоматизации UI-тестирования интернет-магазина [SauceDemo](https://www.saucedemo.com) на Java + Selenide + Cucumber + Allure.

## О проекте

Проект покрывает пользовательские сценарии:
- авторизация (успешная, с неверным паролем, с пустыми полями);
- работа с корзиной (добавление, удаление, счётчик товаров);
- оформление заказа (успешное, с пропущенными обязательными полями, отмена).

Тесты реализованы в двух подходах:
- **JUnit 5 + Page Object Model** - классический подход с явными проверками;
- **Cucumber (BDD) + Gherkin** - сценарии на русском языке, понятные нетехническим специалистам.

## Стек технологий

| Технология | Версия | Назначение |
|---|---|---|
| Java | 21 | Язык разработки |
| Selenide | 7.4.0 | Обёртка над Selenium WebDriver |
| JUnit 5 | 5.10.3 | Тестовый раннер |
| Cucumber | 7.18.1 | BDD-сценарии на Gherkin |
| Allure | 2.27.0 | Отчётность |
| Gradle | 8.13 | Сборка и управление зависимостями |

---

## 📁 Структура проекта
```
src/
└── test/
├── java/org/example/
│ ├── cucumber/                        # BDD-слой
│ │ ├── CommonSteps.java               # Step definitions
│ │ ├── CucumberHooks.java             # @Before / @After для сценариев
│ │ └── CucumberTestRunner.java        # Раннер Cucumber
│ ├── pages/                           # Page Object Model
│ │ ├── BasePage.java
│ │ ├── LoginPageNoCucumber.java
│ │ ├── ProductsPageNoCucumber.java
│ │ ├── CartPageNoCucumber.java
│ │ └── CheckoutPageNoCucumber.java
│ ├── tests/                           # JUnit 5 тесты
│ │ ├── BaseTest.java
│ │ ├── LoginTests.java
│ │ ├── CartTests.java
│ │ └── CheckoutTests.java
│ └── utils/
│ └── TestData.java                    # Тестовые данные
└── resources/
├── allure.properties
└── features/                          # Gherkin-фичи
├── login.feature
├── cart.feature
└── checkout.feature
```

---

## Покрытие тестами

### Авторизация
- Успешный вход с валидными данными
- Вход с неверным паролем
- Вход с пустыми полями

### Корзина
- Добавление одного товара
- Добавление нескольких товаров
- Удаление товара
- Проверка счётчика корзины

### Оформление заказа
- Успешное оформление
- Валидация обязательных полей (имя, фамилия, индекс)
- Отмена оформления

## Запуск

### Требования
- JDK 21
- Установленный браузер (Chrome / Firefox / Edge)

#### Все тесты
`./gradlew test`
#### Только JUnit-тесты
`./gradlew test --tests "org.example.tests.*"`
#### Только Cucumber-сценарии
`./gradlew test --tests "org.example.cucumber.CucumberTestRunner"`

---

## Конфигурация
Параметры задаются в gradle.properties или через -D при запуске:

| Параметр | По умолчанию | Описание |
|-|-|-|
| browser | chrome | chrome / firefox / edge |
| headless | false | Запуск без UI |
| browserSize | 1600x900 | Размер окна браузера |
| baseUrl | https://www.saucedemo.com | Базовый URL приложения |

## Allure-отчёт
После прогона тестов:

`./gradlew allureServe`
Отчёт содержит:
- разбивку по фичам и сценариям (Cucumber);
- шаги с параметрами;
- скриншоты при падении теста;
- стек-трейсы.

## Архитектурные решения
- Page Object Model - каждая страница инкапсулирована в отдельном классе, локаторы не «протекают» в тесты.
- Fluent API - методы Page Object возвращают this, что делает цепочки вызовов читаемыми.
- Единая точка настройки браузера - параметры читаются из gradle.properties и применяются в BaseTest и CucumberHooks.
- BDD-слой на русском языке - Gherkin-сценарии написаны на русском для читаемости нетехническими специалистами.
- Allure + Cucumber - отчёты строятся на основе Gherkin-сценариев без ручного добавления @Step.