package ru.bellintegrator.practice;

import org.springframework.util.NumberUtils;

public class StringChecker {

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static boolean isNumeric(String str) {
        try {
            NumberUtils.parseNumber(str, Long.class);
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }
}
