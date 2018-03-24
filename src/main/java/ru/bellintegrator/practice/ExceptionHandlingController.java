package ru.bellintegrator.practice;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.exception.NotValidParamException;

import javax.management.InstanceAlreadyExistsException;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {
    private static final String SERVER_ERROR_MESSAGE = "Внутренняя ошибка сервера";

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NotValidParamException.class)
    public ResponseView handleNotValidParamException(NotValidParamException ex) {
        return new ResponseView().error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseView handleNotValidParamException(NotFoundException ex) {
        return new ResponseView().error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = InstanceAlreadyExistsException.class)
    public ResponseView handleInstanceAlreadyExistsException(InstanceAlreadyExistsException ex) {
        return new ResponseView().error(ex.getMessage());
    }

    /*@ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = Exception.class)
    public ResponseView handleException(Exception ex) {
        return new ResponseView().error(SERVER_ERROR_MESSAGE);
    }*/
}