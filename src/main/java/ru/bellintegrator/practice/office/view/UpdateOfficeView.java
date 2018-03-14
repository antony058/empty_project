package ru.bellintegrator.practice.office.view;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UpdateOfficeView {

    @NotNull
    @NotEmpty
    public String id;

    public String name;

    public String address;

    public String phone;

    public Boolean isActive;

    public UpdateOfficeView() {

    }
}
