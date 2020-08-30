package com.baeldung.spring.cloud.openservicebroker.services;

import com.baeldung.spring.cloud.openservicebroker.mail.MailService;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MailServiceInstanceBindingService implements ServiceInstanceBindingService {

    private final MailService mailService;

    public MailServiceInstanceBindingService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public Mono<CreateServiceInstanceBindingResponse> createServiceInstanceBinding(
        CreateServiceInstanceBindingRequest request) {
        return Mono.just(CreateServiceInstanceAppBindingResponse.builder())
            .flatMap(responseBuilder -> mailService.serviceBindingExists(
                request.getServiceInstanceId(), request.getBindingId())
                .flatMap(exists -> {
                    if (exists) {
                        return mailService.getServiceBinding(
                            request.getServiceInstanceId(), request.getBindingId())
                            .flatMap(serviceBinding -> Mono.just(responseBuilder
                                .bindingExisted(true)
                                .credentials(serviceBinding.getCredentials())
                                .build()));
                    } else {
                        return mailService.createServiceBinding(
                            request.getServiceInstanceId(), request.getBindingId())
                            .switchIfEmpty(Mono.error(
                                new ServiceInstanceDoesNotExistException(
                                    request.getServiceInstanceId())))
                            .flatMap(mailServiceBinding -> Mono.just(responseBuilder
                                .bindingExisted(false)
                                .credentials(mailServiceBinding.getCredentials())
                                .build()));
                    }
                }));
    }

    @Override
    public Mono<GetServiceInstanceBindingResponse> getServiceInstanceBinding(GetServiceInstanceBindingRequest request) {
        return mailService.getServiceBinding(request.getServiceInstanceId(), request.getBindingId())
            .switchIfEmpty(Mono.error(new ServiceInstanceBindingDoesNotExistException(request.getBindingId())))
            .flatMap(mailServiceBinding -> Mono.just(GetServiceInstanceAppBindingResponse.builder()
                .credentials(mailServiceBinding.getCredentials())
                .build()));
    }

    @Override
    public Mono<DeleteServiceInstanceBindingResponse> deleteServiceInstanceBinding(
        DeleteServiceInstanceBindingRequest request) {
        return mailService.serviceBindingExists(request.getServiceInstanceId(), request.getBindingId())
            .flatMap(exists -> {
                if (exists) {
                    return mailService.deleteServiceBinding(request.getServiceInstanceId())
                        .thenReturn(DeleteServiceInstanceBindingResponse.builder().build());
                } else {
                    return Mono.error(new ServiceInstanceBindingDoesNotExistException(request.getBindingId()));
                }
            });
    }
}
