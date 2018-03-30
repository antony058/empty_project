package ru.bellintegrator.practice.organization.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrganizationView {
    /*
    * Выходная(Response) View для ответа пользователю
    *
    * Также используется как входная(Request) View для
    * получения данных от пользователя
     */

    @ApiModelProperty(hidden = true)
    public Long id;

    @NotNull(message = "не может быть пустым")
    @Size(message = "не может быть пустым", min = 1)
    public String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String fullName;

    @NotNull(message = "не может быть пустым")
    @Size(message = "не может быть пустым", min = 1)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String inn;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String kpp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String phone;

    @NotNull(message = "не может быть пустым")
    public Boolean isActive;

    public OrganizationView() {

    }
}
