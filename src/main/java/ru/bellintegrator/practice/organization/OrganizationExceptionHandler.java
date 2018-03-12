package ru.bellintegrator.practice.organization;

import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.exception.CustomRuntimeException;
import ru.bellintegrator.practice.ViewWrapper;

@ControllerAdvice
@RestController
public class OrganizationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = CustomRuntimeException.class)
    public ViewWrapper handleBaseException(CustomRuntimeException ex) {
        ViewWrapper viewWrapper = new ViewWrapper();
        viewWrapper.setError(ex.getMessage());

        return viewWrapper;
    }
}