package ru.bellintegrator.practice.user.service.impl;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.user.dao.UserDAO;
import ru.bellintegrator.practice.user.model.Activation;
import ru.bellintegrator.practice.user.model.User;
import ru.bellintegrator.practice.user.service.UserService;
import ru.bellintegrator.practice.user.util.StringUtils;
import ru.bellintegrator.practice.user.view.LoginUserView;
import ru.bellintegrator.practice.user.view.UserView;

import javax.management.InstanceAlreadyExistsException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
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
          view.email = u.getEmail();

          return view;
        };

        return all.stream()
                .map(mapUser)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void register(UserView view) throws InstanceAlreadyExistsException, NoSuchAlgorithmException {
        User user = dao.loadByLogin(view.login);
        if (user != null)
            throw new InstanceAlreadyExistsException("Пользователь " + view.login + " уже существует");

        String passwordHashCode = StringUtils.sha256Hex(view.password);

        user = new User();
        user.setLogin(view.login);
        user.setName(view.name);
        user.setPassword(passwordHashCode);
        user.setUserActive(false);
        user.setEmail(view.email);

        String activationHashCode = StringUtils.sha256Hex(StringUtils.generateStringToHash());

        Activation activation = new Activation();
        activation.setCode(activationHashCode);
        activation.setUser(user);

        user.setActivation(activation);

        dao.add(user);

        log.info("Зарегистрирован новый пользователь " + user.getLogin());
    }

    @Override
    public void login(LoginUserView view) throws NoSuchAlgorithmException, NotFoundException {
        // dao.loadByLoginAndPassword(view.login, passwordHashCode);
        // Пожалуй, быстрее найти пользователя по логину (индекс в БД) и сверить хэши паролей в сервисе,
        // чем искать в БД и по логину и по паролю

        User user = dao.loadByLogin(view.getLogin());
        if (user == null)
            throw new NotFoundException("Пользователь " + view.getLogin() + " не найден");

        String passwordHashCode = StringUtils.sha256Hex(view.getPassword());
        if (!user.getPassword().equals(passwordHashCode))
            throw new NotFoundException("Не верный пароль");

        log.info("Авторизован пользователь " + user.getLogin());
    }
}
