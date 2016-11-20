package com.baeldung.factorybean;

import org.springframework.beans.factory.config.AbstractFactoryBean;

//no need to set singleton property because default value is true
public class SingleToolFactory extends AbstractFactoryBean<Tool> {
    private int factoryId;// standard setters and getters
    private int toolId;// standard setters and getters
    private String toolName;// standard setters and getters
    private double toolPrice;// standard setters and getters

    @Override
    public Class<?> getObjectType() {
        return Tool.class;
    }

    @Override
    protected Tool createInstance() throws Exception {
        return new Tool(toolId, toolName, toolPrice);
    }

    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    public int getToolId() {
        return toolId;
    }

    public void setToolId(int toolId) {
        this.toolId = toolId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public double getToolPrice() {
        return toolPrice;
    }

    public void setToolPrice(double toolPrice) {
        this.toolPrice = toolPrice;
    }
}
