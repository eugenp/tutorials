package com.baeldung.collections.mulipletypesinmap;

public class IntegerTypeValue implements DynamicTypeValue {
    private Integer value;

    public IntegerTypeValue(Integer value) {
        this.value = value;
    }

    @Override
    public String valueDescription() {
        if(value == null){
            return "The value is null.";
        }
        return String.format("The value is a %s integer: %d", value > 0 ? "positive" : "negative", value);
    }
}
