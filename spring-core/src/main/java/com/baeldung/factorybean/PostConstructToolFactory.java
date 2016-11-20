package com.baeldung.factorybean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;

public class PostConstructToolFactory implements FactoryBean<Tool> {
    private int factoryId;// standard setters and getters
    private int toolId;// standard setters and getters
    private String toolName;// standard setters and getters
    private double toolPrice;// standard setters and getters

    @Override
    public Tool getObject() throws Exception {
        return new Tool(toolId, toolName, toolPrice);
    }

    @Override
    public Class<?> getObjectType() {
        return Tool.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @PostConstruct
    public void checkParams() {
        if (toolName == null || toolName.equals("")) {
            throw new IllegalArgumentException("tool name cannot be empty");
        }
        if (toolPrice < 0) {
            throw new IllegalArgumentException("tool price should not be less than 0");
        }
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
