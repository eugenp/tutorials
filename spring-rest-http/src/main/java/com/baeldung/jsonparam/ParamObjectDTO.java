package com.baeldung.jsonparam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamObjectDTO {
    private String type;
    private String color;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ParamObjectDTO{" +
                "type='" + type + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
