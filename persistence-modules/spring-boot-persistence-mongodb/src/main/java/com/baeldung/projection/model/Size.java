package com.baeldung.projection.model;

import java.util.Objects;

public class Size {

    private Double height;
    private Double width;
    private String uom;

    public Size(Double height, Double width, String uom) {
        this.height = height;
        this.width = width;
        this.uom = uom;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Size size = (Size) o;
        return Objects.equals(height, size.height) && Objects.equals(width, size.width) && Objects.equals(uom, size.uom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, uom);
    }

}
