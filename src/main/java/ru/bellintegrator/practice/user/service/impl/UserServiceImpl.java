package ru.bellintegrator.practice.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.user.dao.UserDAO;
import ru.bellintegrator.practice.user.model.User;
import ru.bellintegrator.practice.user.service.UserService;
import ru.bellintegrator.practice.user.view.UserView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService {
    private final UserDAO dao;

    @Autowired
    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserView> users() {
        List<User> all = dao.all();

        Function<User, UserView> mapUser = u -> {
          UserView view = new UserView();
          view.login = u.getLogin();
          view.password = u.getPassword();
          view.name = u.getName();
          view.isActive = u.getUserActive();

          return view;
        };

        return all.stream()
                .map(mapUser)
                .collect(Collectors.toList());
    }
}
