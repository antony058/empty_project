package ru.bellintegrator.practice.office.view;

import javax.validation.constraints.NotNull;

public class UpdateOfficeView {

    @NotNull
    public Long id;

    public String name;

    public String address;

    public String phone;

    public Boolean isActive;

    public UpdateOfficeView() {

    }
}
