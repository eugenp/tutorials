package com.baeldung.factorybean;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

public class PostConstructToolFactory implements FactoryBean<Tool> {
    private int factoryId;
    private int toolId;
    private String toolName;
    private double toolPrice;

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
        checkArgument(!StringUtils.isEmpty(toolName), "tool name cannot be empty");
        checkArgument(toolPrice >= 0, "tool price should not be less than 0");
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
