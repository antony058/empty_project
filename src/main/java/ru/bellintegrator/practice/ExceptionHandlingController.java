package ru.bellintegrator.practice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.exception.NotValidParamException;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {

    /*
    * Отлавливаем исключение NotValidParamException
    * и записываем сообщение об ошибке в поле error класса-обертки
    *
    *
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NotValidParamException.class)
    public ResponseView handleNotValidParamException(NotValidParamException ex) {
        ResponseView responseView = new ResponseView();
        responseView.setError(ex.getMessage());

        return responseView;
    }

    /*@ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = Exception.class)
    public ResponseView handleException(Exception ex) {
        ResponseView responseView = new ResponseView();
        responseView.setError(ex.getMessage());

        return responseView;
    }*/
}