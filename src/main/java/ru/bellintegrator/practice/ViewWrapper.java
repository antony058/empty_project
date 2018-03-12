package ru.bellintegrator.practice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ViewWrapper {
    @JsonProperty(value = "data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Object object;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String error;

    public ViewWrapper() {

    }

    public ViewWrapper(Object object) {
        this.object = object;
    }

    public void setSuccess(String result) {
        this.result = result;
    }

    public void setError(String error) {
        this.error = error;
    }
}
