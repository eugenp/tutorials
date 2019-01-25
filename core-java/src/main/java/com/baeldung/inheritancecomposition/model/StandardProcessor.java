package com.baeldung.inheritancecomposition.model;

/**
 * 标准处理器
 * @author zn.wang
 */
public class StandardProcessor implements Processor {

    private String model;

    public StandardProcessor(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }
    
    @Override
    public String toString() {
        return "Processor{" + "model=" + model + "}";
    }
}
