package ru.bellintegrator.practice.office.view;

import com.fasterxml.jackson.annotation.JsonInclude;

public class OfficeView {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean isActive;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long orgId;

    public OfficeView() {

    }
}
