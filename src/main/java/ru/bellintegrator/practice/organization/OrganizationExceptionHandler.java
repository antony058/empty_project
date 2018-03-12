package ru.bellintegrator.practice.organization;

import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.exception.NotValidParamRuntimeException;
import ru.bellintegrator.practice.ViewWrapper;

@ControllerAdvice
@RestController
public class OrganizationExceptionHandler {

    /*
    * Отлавливаем исключение NotValidParamRuntimeException
    * и записываем сообщение об ошибке в поле error класса-обертки
    *
    *
     */
    @ResponseBody
    @ExceptionHandler(value = NotValidParamRuntimeException.class)
    public ViewWrapper handleBaseException(NotValidParamRuntimeException ex) {
        ViewWrapper viewWrapper = new ViewWrapper();
        viewWrapper.setError(ex.getMessage());

        return viewWrapper;
    }
}