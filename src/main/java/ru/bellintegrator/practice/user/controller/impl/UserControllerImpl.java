package ru.bellintegrator.practice.user.controller.impl;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.utils.ErrorUtils;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.exception.NotValidParamException;
import ru.bellintegrator.practice.user.controller.UserController;
import ru.bellintegrator.practice.user.service.UserService;
import ru.bellintegrator.practice.user.view.LoginUserView;
import ru.bellintegrator.practice.user.view.UserView;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @ApiOperation(value = "registerUser", nickname = "registerUser", httpMethod = "POST")
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ResponseView registerUser(@Valid @RequestBody UserView view, BindingResult bindingResult)
            throws InstanceAlreadyExistsException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException(ErrorUtils.makeRequiredFieldsList(bindingResult.getFieldErrors()));

        userService.register(view);
        return new ResponseView().success();
    }

    @Override
    @ApiOperation(value = "loginUser", nickname = "loginUser", httpMethod = "POST")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ResponseView loginUser(@Valid @RequestBody LoginUserView view, BindingResult bindingResult)
            throws NotFoundException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException(ErrorUtils.makeRequiredFieldsList(bindingResult.getFieldErrors()));

        userService.login(view);
        return new ResponseView().success();
    }
}
