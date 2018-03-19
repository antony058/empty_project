package ru.bellintegrator.practice.organization.view;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UpdateOrganizationView {
    /*
    * Входная(Request) View для получения данных от пользователя,
    * которые десиреализуются в этот класс
    *
    * Используется для запроса пользователя на изменение данных
     */

    @NotEmpty
    @NotNull
    public String id;

    public String name;

    public String fullName;

    public String inn;

    public String kpp;

    public String address;

    public String phone;

    public Boolean isActive;

    public UpdateOrganizationView() {

    }
}
