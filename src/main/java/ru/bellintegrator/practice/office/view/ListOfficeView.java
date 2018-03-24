package ru.bellintegrator.practice.office.view;

import javax.validation.constraints.NotNull;

public class ListOfficeView {

    @NotNull
    public Long orgId;

    public String name;

    public String phone;

    public Boolean isActive;
}
