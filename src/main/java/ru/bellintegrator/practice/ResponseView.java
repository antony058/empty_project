package ru.bellintegrator.practice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.NumberUtils;

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

    @JsonIgnore
    private static final String success = "success";

    public ResponseView data(Object object) {
        this.object = object;
        return this;
    }

    public ResponseView success() {
        this.result = success;
        return this;
    }

    public ResponseView error(String error) {
        this.error = error;
        return this;
    }
}
