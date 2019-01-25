package com.baeldung.inheritancecomposition.model;

/**
 * 标准声卡
 * @author zn.wang
 */
public class StandardSoundCard implements SoundCard {

    /**
     * 品牌
     */
    private String brand;

    public StandardSoundCard(String brand) {
        this.brand = brand;
    }
    
    @Override
    public String getBrand() {
        return brand;
    }
    
    @Override
    public String toString() {
        return "SoundCard{" + "brand=" + brand + "}";
    }  
}
