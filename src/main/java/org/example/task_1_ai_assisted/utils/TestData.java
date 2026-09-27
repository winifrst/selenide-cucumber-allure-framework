package org.example.task_1_ai_assisted.utils;

/**
 * Класс для хранения тестовых данных
 * Используется для централизованного управления данными
 */
public class TestData {

    // Данные для авторизации
    public static final String VALID_USERNAME = "standard_user";
    public static final String VALID_PASSWORD = "secret_sauce";
    public static final String INVALID_PASSWORD = "wrong_password";
    public static final String EMPTY_STRING = "";

    // Сообщения об ошибках
    public static final String ERROR_INVALID_CREDENTIALS =
            "Epic sadface: Username and password do not match any user in this service";
    public static final String ERROR_USERNAME_REQUIRED =
            "Epic sadface: Username is required";
    public static final String ERROR_PASSWORD_REQUIRED =
            "Epic sadface: Password is required";
    public static final String ERROR_FIRST_NAME_REQUIRED =
            "Error: First Name is required";
    public static final String ERROR_LAST_NAME_REQUIRED =
            "Error: Last Name is required";
    public static final String ERROR_POSTAL_CODE_REQUIRED =
            "Error: Postal Code is required";

    // Данные для оформления заказа
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String POSTAL_CODE = "12345";
    public static final String FIRST_NAME_EMPTY = "";
    public static final String LAST_NAME_EMPTY = "";
    public static final String POSTAL_CODE_EMPTY = "";
}