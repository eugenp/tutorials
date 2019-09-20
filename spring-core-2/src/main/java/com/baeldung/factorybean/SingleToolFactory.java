package com.baeldung.factorybean;

import org.springframework.beans.factory.config.AbstractFactoryBean;

//no need to set singleton property because default value is true
public class SingleToolFactory extends AbstractFactoryBean<Tool> {
    
    private int factoryId;
    private int toolId;

    @Override
    public Class<?> getObjectType() {
        return Tool.class;
    }

    @Override
    protected Tool createInstance() throws Exception {
        return new Tool(toolId);
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
}
