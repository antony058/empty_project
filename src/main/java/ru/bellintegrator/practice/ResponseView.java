package ru.bellintegrator.practice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseView {
    /*
    * Класс ResponseView используется в роли обертки для выходного View
    * Перед отправкой пользователю объект этого класса будет сериализован в JSON
     */

    @JsonProperty(value = "data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Object object;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String error;

    public ResponseView() {

    }

    public ResponseView(Object object) {
        this.object = object;
    }

    public void setSuccess(String result) {
        this.result = result;
    }

    public void setError(String error) {
        this.error = error;
    }
}
