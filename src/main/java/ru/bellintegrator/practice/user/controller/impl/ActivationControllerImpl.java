package ru.bellintegrator.practice.user.controller.impl;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.user.controller.ActivationController;
import ru.bellintegrator.practice.user.service.ActivationService;
import ru.bellintegrator.practice.user.view.ActivationView;

import java.util.List;

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
    public String checkActivationCode(@RequestParam("code") String code) {
        return activationService.checkActivationCode(code);
    }
}
