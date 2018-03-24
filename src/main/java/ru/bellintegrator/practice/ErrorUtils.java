package ru.bellintegrator.practice;

import org.springframework.validation.FieldError;

import java.util.Iterator;
import java.util.List;

public class ErrorUtils {
    /*
    * Метод формирует строку, содержащую сообщение о том,
    * какие поля не прошли валидацию и по какой причине
    *
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
