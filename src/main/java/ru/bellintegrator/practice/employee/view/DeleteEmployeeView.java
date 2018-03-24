package ru.bellintegrator.practice.employee.view;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;

public class DeleteEmployeeView {

    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    public Long id;
}
