package ru.bellintegrator.practice.employee.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

public class EmployeeView {

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long id;

    @NotNull
    @Size(min = 1)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String middleName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String position;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String phone;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer docCode;

    @NotNull
    @Size(min = 1)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String docName;

    @NotNull
    @Size(min = 1)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String docNumber;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Date docDate;

    @NotNull
    @Size(min = 1)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String citizenshipName;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer citizenshipCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean isIdentified;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long officeId;
}
