package ru.bellintegrator.practice.testdata.service.user;

import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import ru.bellintegrator.practice.user.dao.UserDAO;
import ru.bellintegrator.practice.user.model.User;
import ru.bellintegrator.practice.user.service.UserService;
import ru.bellintegrator.practice.user.service.impl.UserServiceImpl;
import ru.bellintegrator.practice.user.utils.HashGenerator;
import ru.bellintegrator.practice.user.view.LoginUserView;
import ru.bellintegrator.practice.user.view.UserView;

import javax.management.InstanceAlreadyExistsException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserDAO userDAO;

    @Before
    public void setUp() {
        userDAO = mock(UserDAO.class);
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void userRegisterSuccessTest() throws NoSuchAlgorithmException, InstanceAlreadyExistsException {
        when(userDAO.loadByLogin("some_user")).thenReturn(null);

        UserView view = createUserView();
        userService.register(view);

        verify(userDAO).add(any());
    }

    @Test(expected = InstanceAlreadyExistsException.class)
    public void userRegisterInstanceAlreadyExistsExceptionTest_userAlreadyExist() throws NoSuchAlgorithmException, InstanceAlreadyExistsException {
        User user = createUser();
        when(userDAO.loadByLogin("some_user")).thenReturn(user);

        UserView view = createUserView();
        userService.register(view);
    }

    @Test
    public void userLoginSuccessTest() throws NotFoundException, NoSuchAlgorithmException {
        User user = createUser();
        when(userDAO.loadByLogin("some_user")).thenReturn(user);

        LoginUserView view = createLoginUserView();
        userService.login(view);
    }

    @Test(expected = NotFoundException.class)
    public void userLoginNotFoundExceptionTest_userNotFound() throws NotFoundException, NoSuchAlgorithmException {
        when(userDAO.loadByLogin("some_user")).thenReturn(null);

        LoginUserView view = createLoginUserView();
        userService.login(view);
    }

    @Test(expected = NotFoundException.class)
    public void userLoginNotFoundExceptionTest_incorrectPassword() throws NoSuchAlgorithmException, NotFoundException {
        User user = createUser();
        when(userDAO.loadByLogin("some_user")).thenReturn(user);

        LoginUserView view = createLoginUserView();
        view.setPassword("p"); // задаем пароль отличный от того, который у User
        userService.login(view);
    }

    private LoginUserView createLoginUserView() {
        LoginUserView view = new LoginUserView();
        view.setLogin("some_user");
        view.setPassword("password");

        return view;
    }

    private UserView createUserView() {
        UserView view = new UserView();
        view.login = "some_user";
        view.password = "password";

        return view;
    }

    private User createUser() throws NoSuchAlgorithmException {
        User user = new User();
        user.setLogin("some_user");
        user.setPassword(HashGenerator.sha256Hex("password"));

        return user;
    }
}
