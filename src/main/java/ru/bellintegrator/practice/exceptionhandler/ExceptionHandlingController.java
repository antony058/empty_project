package ru.bellintegrator.practice.exceptionhandler;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.exception.NotValidParamException;

import javax.management.InstanceAlreadyExistsException;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {
    private final Logger log = LoggerFactory.getLogger(ExceptionHandlingController.class);

    private static final String SERVER_ERROR_MESSAGE = "Внутренняя ошибка сервера";

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NotValidParamException.class)
    public ResponseView handleNotValidParamException(NotValidParamException ex) {
        log.error("", ex);

        return new ResponseView().error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseView handleNotFoundException(NotFoundException ex) {
        log.error("", ex);

        return new ResponseView().error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = InstanceAlreadyExistsException.class)
    public ResponseView handleInstanceAlreadyExistsException(InstanceAlreadyExistsException ex) {
        log.error("", ex);

        return new ResponseView().error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = Throwable.class)
    public ResponseView handleException(Throwable ex) {
        log.error("", ex);

        return new ResponseView().error(SERVER_ERROR_MESSAGE);
    }
}