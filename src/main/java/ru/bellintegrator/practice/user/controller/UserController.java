package ru.bellintegrator.practice.user.controller;

import javassist.NotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.user.view.LoginUserView;
import ru.bellintegrator.practice.user.view.UserView;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

public interface UserController {
    ResponseView registerUser(@Valid @RequestBody UserView view, BindingResult bindingResult)
            throws InstanceAlreadyExistsException, NoSuchAlgorithmException;

    ResponseView loginUser(@Valid @RequestBody LoginUserView view, BindingResult bindingResult)
            throws NotFoundException, NoSuchAlgorithmException;
}
