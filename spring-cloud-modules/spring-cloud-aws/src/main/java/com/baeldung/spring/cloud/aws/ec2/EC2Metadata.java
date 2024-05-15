package com.baeldung.spring.cloud.aws.ec2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class EC2Metadata {

    @Value("${ami-id:N/A}")
    private String amiId;

    @Value("${hostname:N/A}")
    private String hostname;

    @Value("${instance-type:N/A}")
    private String instanceType;

    @Value("${services/domain:N/A}")
    private String serviceDomain;

    @Value("#{instanceData['Name'] ?: 'N/A'}")
    private String name;

    public String getAmiId() {
        return amiId;
    }

    public String getHostname() {
        return hostname;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public String getServiceDomain() {
        return serviceDomain;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EC2Metadata [amiId=");
        builder.append(amiId);
        builder.append(", hostname=");
        builder.append(hostname);
        builder.append(", instanceType=");
        builder.append(instanceType);
        builder.append(", serviceDomain=");
        builder.append(serviceDomain);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
