package com.baeldung.nullconversion;

public class ZipCode implements Cloneable {

    private String code;

    public ZipCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    protected ZipCode clone() {
        try {
            return (ZipCode) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCode(String code) {
        this.code = code;
    }
}
