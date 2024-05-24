package com.baeldung.bulkandbatchapi;

import jakarta.validation.constraints.NotNull;

public class BatchRequest {
    @NotNull
    private ResourceType resourceType;
    @NotNull
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
