package com.hospital.appointment.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final String PHONE_REGEX =
            "^[6-9]\\d{9}$";

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private ValidationUtil() {
        // Utility class
    }

    public static boolean isValidName(String name) {

        return name != null
                && !name.trim().isEmpty()
                && name.trim().length() >= 2;
    }

    public static boolean isValidAge(int age) {

        return age >= 1 && age <= 120;
    }

    public static boolean isValidPhone(String phone) {

        return phone != null
                && Pattern.matches(
                        PHONE_REGEX,
                        phone
                );
    }

    public static boolean isValidEmail(String email) {

        return email != null
                && Pattern.matches(
                        EMAIL_REGEX,
                        email
                );
    }

    public static boolean isValidFee(double fee) {

        return fee >= 0;
    }
}