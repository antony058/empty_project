package ru.bellintegrator.practice.organization.view;

import javax.validation.constraints.NotNull;

public class UpdateOrganizationView {
    /*
    * Входная(Request) View для получения данных от пользователя,
    * которые десиреализуются в этот класс
    *
    * Используется для запроса пользователя на изменение данных
     */

    @NotNull
    public Long id;

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
