package ru.bellintegrator.practice.user.service;

import javassist.NotFoundException;
import ru.bellintegrator.practice.user.view.LoginUserView;
import ru.bellintegrator.practice.user.view.UserView;

import javax.management.InstanceAlreadyExistsException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    void register(UserView view) throws InstanceAlreadyExistsException, NoSuchAlgorithmException;

    void login(LoginUserView view) throws NoSuchAlgorithmException, NotFoundException;
}
