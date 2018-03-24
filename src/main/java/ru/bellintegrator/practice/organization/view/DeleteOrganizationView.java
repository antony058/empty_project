package ru.bellintegrator.practice.organization.view;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DeleteOrganizationView {

    @NotNull(message = "не может быть пустым")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 1, message = "не может быть меньше 1")
    public Long id;
}
