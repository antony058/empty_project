package ru.bellintegrator.practice.organization;

import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.exception.NotValidParamRuntimeException;

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
    public ResponseView handleBaseException(NotValidParamRuntimeException ex) {
        ResponseView responseView = new ResponseView();
        responseView.setError(ex.getMessage());

        return responseView;
    }
}