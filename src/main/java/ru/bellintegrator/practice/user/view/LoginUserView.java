package ru.bellintegrator.practice.user.view;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginUserView {

    @NotNull(message = "не может быть пустым")
    @Size(min = 5, message = "login не может содержать меньше 5 символов")
    private String login;

    @NotNull(message = "не может быть пустым")
    @Size(min = 5, message = "не может содержать меньше 5 символов")
    private String password;

    public LoginUserView() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
