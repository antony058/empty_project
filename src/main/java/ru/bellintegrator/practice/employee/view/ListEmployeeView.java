package ru.bellintegrator.practice.employee.view;

import javax.validation.constraints.NotNull;

public class ListEmployeeView {

    @NotNull
    public Long officeId;

    public String firstName;

    public String lastName;

    public String middleName;

    public String position;

    public Integer docCode;

    public Integer citizenshipCode;
}
