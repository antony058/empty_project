package ru.bellintegrator.practice.user.controller;

import javassist.NotFoundException;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bellintegrator.practice.ResponseView;

import java.security.NoSuchAlgorithmException;

public interface ActivationController {
    ResponseView activateUser(@RequestParam String code) throws NotFoundException, NoSuchAlgorithmException;
}
