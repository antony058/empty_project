package ru.bellintegrator.practice.office.view;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ListOfficeView {

    @NotNull
    @NotEmpty
    public String orgId;

    public String name;

    public String phone;

    public Boolean isActive;
}
