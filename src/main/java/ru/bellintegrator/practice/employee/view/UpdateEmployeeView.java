package ru.bellintegrator.practice.employee.view;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class UpdateEmployeeView {

    @NotNull
    @NotEmpty
    public String id;

    public String firstName;

    public String lastName;

    public String middleName;

    public String position;

    public String phone;

    public String docName;

    public String docNumber;

    public Date docDate;

    public String citizenshipName;

    public Integer citizenshipCode;

    public Boolean isIdentified;
}
