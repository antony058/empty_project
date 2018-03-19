package ru.bellintegrator.practice.organization.view;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DeleteOrganizationView {

    @NotNull
    @NotEmpty
    public String id;
}
