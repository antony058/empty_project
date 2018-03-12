package ru.bellintegrator.practice.organization.view;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ListOrganizationView {
    /*
    * Входная(Request) View для получения данных от пользователя,
    * которые десиреализуются в этот класс
    *
    * Используется для запроса пользователя на получение списка организаций
     */

    @NotNull
    public String name;

    public String inn;

    public Boolean isActive;

    public ListOrganizationView() {

    }
}
