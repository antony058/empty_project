package ru.bellintegrator.practice.office.view;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DeleteOfficeView {

    @NotNull
    @NotEmpty
    public String id;
}
