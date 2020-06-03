package com.baeldung.spring.cloud.openservicebroker.services;

import com.baeldung.spring.cloud.openservicebroker.mail.MailService;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.instance.GetServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.GetServiceInstanceResponse;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MailServiceInstanceService implements ServiceInstanceService {

    private final MailService mailService;

    public MailServiceInstanceService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public Mono<CreateServiceInstanceResponse> createServiceInstance(CreateServiceInstanceRequest request) {
        return Mono.just(request.getServiceInstanceId())
            .flatMap(instanceId -> Mono.just(CreateServiceInstanceResponse.builder())
                .flatMap(responseBuilder -> mailService.serviceInstanceExists(instanceId)
                    .flatMap(exists -> {
                        if (exists) {
                            return mailService.getServiceInstance(instanceId)
                                .flatMap(mailServiceInstance -> Mono.just(responseBuilder
                                    .instanceExisted(true)
                                    .dashboardUrl(mailServiceInstance.getDashboardUrl())
                                    .build()));
                        } else {
                            return mailService.createServiceInstance(
                                instanceId, request.getServiceDefinitionId(), request.getPlanId())
                                .flatMap(mailServiceInstance -> Mono.just(responseBuilder
                                    .instanceExisted(false)
                                    .dashboardUrl(mailServiceInstance.getDashboardUrl())
                                    .build()));
                        }
                    })));
    }

    @Override
    public Mono<DeleteServiceInstanceResponse> deleteServiceInstance(DeleteServiceInstanceRequest request) {
        return Mono.just(request.getServiceInstanceId())
            .flatMap(instanceId -> mailService.serviceInstanceExists(instanceId)
                .flatMap(exists -> {
                    if (exists) {
                        return mailService.deleteServiceInstance(instanceId)
                            .thenReturn(DeleteServiceInstanceResponse.builder().build());
                    } else {
                        return Mono.error(new ServiceInstanceDoesNotExistException(instanceId));
                    }
                }));
    }

    @Override
    public Mono<GetServiceInstanceResponse> getServiceInstance(GetServiceInstanceRequest request) {
        return Mono.just(request.getServiceInstanceId())
            .flatMap(instanceId -> mailService.getServiceInstance(instanceId)
                .switchIfEmpty(Mono.error(new ServiceInstanceDoesNotExistException(instanceId)))
                .flatMap(serviceInstance -> Mono.just(GetServiceInstanceResponse.builder()
                    .serviceDefinitionId(serviceInstance.getServiceDefinitionId())
                    .planId(serviceInstance.getPlanId())
                    .dashboardUrl(serviceInstance.getDashboardUrl())
                    .build())));
    }
}
