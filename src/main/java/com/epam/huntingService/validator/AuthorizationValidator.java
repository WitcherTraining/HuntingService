package com.epam.huntingService.validator;

public class AuthorizationValidator {

    private static final String MAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9~!@#$%^&*]{6,}";
    private static final String PHONE_REGEX = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public static boolean validateMailRegex(String login) {
        return login.matches(MAIL_REGEX);
    }

    public static boolean validatePasswordRegex(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validatePhoneNumber(String phone) {
        return phone.matches(PHONE_REGEX);
    }

    public static boolean checkPassword(String password, String passwordInDataBase) {
        return passwordInDataBase.equals(password);
    }

}
