package ru.bellintegrator.practice.user.controller.impl;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.StringChecker;
import ru.bellintegrator.practice.exception.NotValidParamException;
import ru.bellintegrator.practice.user.controller.ActivationController;
import ru.bellintegrator.practice.user.service.ActivationService;

import java.security.NoSuchAlgorithmException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class ActivationControllerImpl implements ActivationController {
    private final ActivationService activationService;

    @Autowired
    public ActivationControllerImpl(ActivationService activationService) {
        this.activationService = activationService;
    }

    @Override
    @ApiOperation(value = "getActivations", nickname = "getActivations", httpMethod = "GET")
    @RequestMapping(value = "/activation", method = {RequestMethod.GET})
    public ResponseView activateUser(@RequestParam("code") String code) throws NotFoundException, NoSuchAlgorithmException {
        if (StringChecker.isNullOrEmpty(code))
            throw new NotValidParamException("Параметр code не доллжен быть пустым");
        activationService.activate(code);

        return new ResponseView().success();
    }
}
