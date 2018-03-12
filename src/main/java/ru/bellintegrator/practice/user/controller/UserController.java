package ru.bellintegrator.practice.user.controller;

import ru.bellintegrator.practice.user.view.UserView;

import java.util.List;

public interface UserController {
    List<UserView> users();
}
