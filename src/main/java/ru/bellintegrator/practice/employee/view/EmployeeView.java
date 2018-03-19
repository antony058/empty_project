package ru.bellintegrator.practice.employee.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class EmployeeView {

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String secondName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String middleName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String position;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String docName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String docNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Date docDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String citizenshipName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer citizenshipCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean isIdentified;

    @NotNull
    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String officeId;
}
