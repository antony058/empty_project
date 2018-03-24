package ru.bellintegrator.practice.user.dao;

import javassist.NotFoundException;
import ru.bellintegrator.practice.user.model.User;

import java.util.List;

public interface UserDAO {

    List<User> all();

    void add(User user);

    User loadByLogin(String login);

    User loadByLoginAndPassword(String login, String password) throws NotFoundException;
}
