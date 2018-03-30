package ru.bellintegrator.practice.utils;

import org.springframework.validation.FieldError;

import java.util.Iterator;
import java.util.List;

public class ErrorUtils {
    /*
    * Метод формирует строку, содержащую сообщение о том,
    * какие поля не прошли валидацию и по какой причине
    *
    * Пример выходного сообщения:
    * "[password не может быть пустым, login не может содержать меньше 5 символов, email не прошел валидацию]"
     */
    public static String makeRequiredFieldsList(List<FieldError> errorList) {
        StringBuilder builder = new StringBuilder();
        Iterator<FieldError> iterator = errorList.iterator();

        if (!iterator.hasNext()) {
            return "";
        }

        builder.append("[").append(getErrorMessage(iterator.next()));

        while (iterator.hasNext()) {
            builder.append(", ").append(getErrorMessage(iterator.next()));
        }

        builder.append("]");

        return builder.toString();
    }

    /*
    * Метод возвращает строку конкретного поля и его ошибки
     */
    private static String getErrorMessage(FieldError fieldError) {
        return fieldError.getField() + " " + fieldError.getDefaultMessage();
    }
}
