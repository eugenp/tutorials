package com.baeldung.spring.cloud.openservicebroker.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MailService {

    public static final String URI_KEY = "uri";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    private final String mailDashboardBaseURL;
    private final String mailSystemBaseURL;

    private Map<String, MailServiceInstance> mailServices = new HashMap<>();

    private Map<String, MailServiceBinding> mailServiceBindings = new HashMap<>();

    public MailService(@Value("${mail.system.dashboard.base-url}") String mailDashboardBaseURL,
        @Value("${mail.system.base-url}") String mailSystemBaseURL) {
        this.mailDashboardBaseURL = mailDashboardBaseURL;
        this.mailSystemBaseURL = mailSystemBaseURL;
    }

    public Mono<MailServiceInstance> createServiceInstance(String instanceId, String serviceDefinitionId, String planId) {
        MailServiceInstance mailServiceInstance = new MailServiceInstance(
            instanceId, serviceDefinitionId, planId, mailDashboardBaseURL + instanceId);
        mailServices.put(instanceId, mailServiceInstance);
        return Mono.just(mailServiceInstance);
    }

    public Mono<Boolean> serviceInstanceExists(String instanceId) {
        return Mono.just(mailServices.containsKey(instanceId));
    }

    public Mono<MailServiceInstance> getServiceInstance(String instanceId) {
        if (mailServices.containsKey(instanceId)) {
            return Mono.just(mailServices.get(instanceId));
        }
        return Mono.empty();
    }

    public Mono<Void> deleteServiceInstance(String instanceId) {
        mailServices.remove(instanceId);
        mailServiceBindings.remove(instanceId);
        return Mono.empty();
    }

    public Mono<MailServiceBinding> createServiceBinding(String instanceId, String bindingId) {
        return this.serviceInstanceExists(instanceId)
            .flatMap(exists -> {
                if (exists) {
                    MailServiceBinding mailServiceBinding =
                        new MailServiceBinding(bindingId, buildCredentials(instanceId, bindingId));
                    mailServiceBindings.put(instanceId, mailServiceBinding);
                    return Mono.just(mailServiceBinding);
                } else {
                    return Mono.empty();
                }
            });
    }

    public Mono<Boolean> serviceBindingExists(String instanceId, String bindingId) {
        return Mono.just(mailServiceBindings.containsKey(instanceId) &&
            mailServiceBindings.get(instanceId).getBindingId().equalsIgnoreCase(bindingId));
    }

    public Mono<MailServiceBinding> getServiceBinding(String instanceId, String bindingId) {
        if (mailServiceBindings.containsKey(instanceId) &&
            mailServiceBindings.get(instanceId).getBindingId().equalsIgnoreCase(bindingId)) {
            return Mono.just(mailServiceBindings.get(instanceId));
        }
        return Mono.empty();
    }

    public Mono<Void> deleteServiceBinding(String instanceId) {
        mailServiceBindings.remove(instanceId);
        return Mono.empty();
    }

    private Map<String, Object> buildCredentials(String instanceId, String bindingId) {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put(URI_KEY, mailSystemBaseURL + instanceId);
        credentials.put(USERNAME_KEY, bindingId);
        credentials.put(PASSWORD_KEY, UUID.randomUUID().toString());
        return credentials;
    }

}
