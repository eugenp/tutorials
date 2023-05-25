package com.baeldung.spring.cloud.openservicebroker.mail;

public class MailServiceInstance {

    private String instanceId;
    private String serviceDefinitionId;
    private String planId;
    private String dashboardUrl;

    public MailServiceInstance(String instanceId, String serviceDefinitionId, String planId, String dashboardUrl) {
        this.instanceId = instanceId;
        this.serviceDefinitionId = serviceDefinitionId;
        this.planId = planId;
        this.dashboardUrl = dashboardUrl;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getServiceDefinitionId() {
        return serviceDefinitionId;
    }

    public String getPlanId() {
        return planId;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }
}
