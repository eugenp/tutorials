package com.baeldung.inheritancecomposition.model;

/**
 * 标准内存
 * @author zn.wang
 */
public class StandardMemory implements Memory {
    /**
     * 品牌
     */
    private String brand;
    private String size;

    public StandardMemory(String brand, String size) {
        this.brand = brand;
        this.size = size;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Memory{" + "brand=" + brand + ", size=" + size + "}";
    }
}
