package ru.bellintegrator.practice.exception;

public class NotValidParamRuntimeException extends RuntimeException {

    /*
    * Исключение выбрасывается ответ на
    * не валидные входные параметры
    *
     */
    public NotValidParamRuntimeException(String message) {
        super(message);
    }
}
