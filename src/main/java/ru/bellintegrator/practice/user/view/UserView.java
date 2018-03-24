package ru.bellintegrator.practice.user.view;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserView {

    @NotNull(message = "не может быть пустым")
    @Size(min = 5, message = "не может содержать меньше 5 символов")
    public String login;

    @NotNull(message = "не может быть пустым")
    @Size(min = 5, message = "не может содержать меньше 5 символов")
    public String password;

    @NotNull(message = "не может быть пустым")
    @Size(min = 2, message = "не может содержать меньше 2 символов")
    public String name;

    @NotNull(message = "не может быть пустым")
    @Size(min = 4, message = "не может содержать меньше 4 символов")
    @Email(message = "не прошел валидацию")
    public String email;

    @ApiModelProperty(hidden = true)
    public Boolean isActive;

    public UserView() {

    }
}
