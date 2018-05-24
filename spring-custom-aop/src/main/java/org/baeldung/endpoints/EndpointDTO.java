package org.baeldung.endpoints;

public class EndpointDTO {
    private String id;
    private boolean enabled;
    private boolean sensitive;

    public EndpointDTO(String id, boolean enabled, boolean sensitive) {
        super();
        this.id = id;
        this.enabled = enabled;
        this.sensitive = sensitive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSensitive() {
        return sensitive;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }

}
