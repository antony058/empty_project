package ru.bellintegrator.practice.organization.view;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ListOrganizationView {

    @NotNull
    public String name;

    public String inn;

    public Boolean isActive;

    public ListOrganizationView() {

    }
}
