package ru.bellintegrator.practice.employee.view;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DeleteEmployeeView {

    @NotNull
    @NotEmpty
    public String id;
}
