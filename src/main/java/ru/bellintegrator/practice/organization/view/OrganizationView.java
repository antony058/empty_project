package ru.bellintegrator.practice.organization.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class OrganizationView {
    /*
    * Выходная(Response) View для ответа пользователю
    *
    * Также используется как входная(Request) View для
    * получения данных от пользователя
     */

    @ApiModelProperty(hidden = true)
    public Long id;

    public String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String fullName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String inn;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String kpp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String phone;

    public Boolean isActive;

    public OrganizationView() {

    }
}
