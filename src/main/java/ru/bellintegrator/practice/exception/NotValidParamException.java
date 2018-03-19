package ru.bellintegrator.practice.exception;

public class NotValidParamException extends RuntimeException {

    /*
    * Исключение выбрасывается ответ на
    * не валидные входные параметры
    *
     */
    public NotValidParamException(String message) {
        super(message);
    }
}
