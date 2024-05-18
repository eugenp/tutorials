package com.baeldung.api.bulkandbatch;

public class BatchRequest {

    private String data;
    private ResourceType resourceType;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
