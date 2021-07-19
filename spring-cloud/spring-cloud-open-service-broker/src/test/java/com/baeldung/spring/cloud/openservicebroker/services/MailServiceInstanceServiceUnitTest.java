package com.baeldung.spring.cloud.openservicebroker.services;

import com.baeldung.spring.cloud.openservicebroker.mail.MailService;
import com.baeldung.spring.cloud.openservicebroker.mail.MailServiceInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.GetServiceInstanceRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MailServiceInstanceServiceUnitTest {

    private static final String MAIL_SERVICE_INSTANCE_ID = "test@baeldung.com";
    private static final String MAIL_SERVICE_DEFINITION_ID = "mock-service-definition-id";
    private static final String MAIL_SERVICE_PLAN_ID = "mock-service-plan-id";
    private static final String MAIL_DASHBOARD_URL = "http://localhost:8080/mail-dashboard/test@baeldung.com";

    @Mock
    private MailService mailService;

    private MailServiceInstanceService mailServiceInstanceService;

    @BeforeEach
    public void setUp() {
        initMocks(this);

        this.mailServiceInstanceService = new MailServiceInstanceService(mailService);
    }

    @Test
    public void givenServiceInstanceDoesNotExist_whenCreateServiceInstance_thenProvisionNewService() {
        // given service instance does not exist
        when(mailService.serviceInstanceExists(MAIL_SERVICE_INSTANCE_ID)).thenReturn(Mono.just(false));

        MailServiceInstance serviceInstance = new MailServiceInstance(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_DEFINITION_ID,
            MAIL_SERVICE_PLAN_ID, MAIL_DASHBOARD_URL);
        when(mailService.createServiceInstance(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_DEFINITION_ID, MAIL_SERVICE_PLAN_ID))
            .thenReturn(Mono.just(serviceInstance));

        // when create service instance
        CreateServiceInstanceRequest request = CreateServiceInstanceRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .serviceDefinitionId(MAIL_SERVICE_DEFINITION_ID)
            .planId(MAIL_SERVICE_PLAN_ID)
            .build();

        // then a new service instance is provisioned
        StepVerifier.create(mailServiceInstanceService.createServiceInstance(request))
            .consumeNextWith(response -> {
                assertFalse(response.isInstanceExisted());
                assertEquals(MAIL_DASHBOARD_URL, response.getDashboardUrl());
            })
            .verifyComplete();
    }

    @Test
    public void givenServiceInstanceExists_whenCreateServiceInstance_thenExistingServiceInstanceIsRetrieved() {
        // given service instance exists
        when(mailService.serviceInstanceExists(MAIL_SERVICE_INSTANCE_ID)).thenReturn(Mono.just(true));

        MailServiceInstance serviceInstance = new MailServiceInstance(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_DEFINITION_ID,
            MAIL_SERVICE_PLAN_ID, MAIL_DASHBOARD_URL);
        when(mailService.getServiceInstance(MAIL_SERVICE_INSTANCE_ID)).thenReturn(Mono.just(serviceInstance));

        // when create service instance
        CreateServiceInstanceRequest request = CreateServiceInstanceRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .serviceDefinitionId(MAIL_SERVICE_DEFINITION_ID)
            .planId(MAIL_SERVICE_PLAN_ID)
            .build();

        // then the existing one is retrieved
        StepVerifier.create(mailServiceInstanceService.createServiceInstance(request))
            .consumeNextWith(response -> {
                assertTrue(response.isInstanceExisted());
                assertEquals(MAIL_DASHBOARD_URL, response.getDashboardUrl());
            })
            .verifyComplete();
    }

    @Test
    public void givenServiceInstanceDoesNotExist_whenDeleteServiceInstance_thenException() {
        // given service instance does not exist
        when(mailService.serviceInstanceExists(MAIL_SERVICE_INSTANCE_ID)).thenReturn(Mono.just(false));

        // when delete service instance
        DeleteServiceInstanceRequest request = DeleteServiceInstanceRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .build();

        // then ServiceInstanceDoesNotExistException is thrown
        StepVerifier.create(mailServiceInstanceService.deleteServiceInstance(request))
            .expectErrorMatches(ex -> ex instanceof ServiceInstanceDoesNotExistException)
            .verify();
    }

    @Test
    public void givenServiceInstanceExists_whenDeleteServiceInstance_thenSuccess() {
        // given service instance exists
        when(mailService.serviceInstanceExists(MAIL_SERVICE_INSTANCE_ID)).thenReturn(Mono.just(true));

        // when delete service instance
        when(mailService.deleteServiceInstance(MAIL_SERVICE_INSTANCE_ID)).thenReturn(Mono.empty());

        DeleteServiceInstanceRequest request = DeleteServiceInstanceRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .build();

        // then success
        StepVerifier.create(mailServiceInstanceService.deleteServiceInstance(request))
            .consumeNextWith(response -> {
                assertFalse(response.isAsync());
                assertNull(response.getOperation());
            })
            .verifyComplete();
    }

    @Test
    public void givenServiceInstanceDoesNotExist_whenGetServiceInstance_thenException() {
        // given service instance does not exist
        when(mailService.getServiceInstance(MAIL_SERVICE_INSTANCE_ID)).thenReturn(Mono.empty());

        // when get service instance
        GetServiceInstanceRequest request = GetServiceInstanceRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .build();

        // then ServiceInstanceDoesNotExistException is thrown
        StepVerifier.create(mailServiceInstanceService.getServiceInstance(request))
            .expectErrorMatches(ex -> ex instanceof ServiceInstanceDoesNotExistException)
            .verify();
    }

    @Test
    public void givenServiceInstanceExists_whenGetServiceInstance_thenExistingServiceInstanceIsRetrieved() {
        // given service instance exists
        MailServiceInstance serviceInstance = new MailServiceInstance(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_DEFINITION_ID,
            MAIL_SERVICE_PLAN_ID, MAIL_DASHBOARD_URL);
        when(mailService.getServiceInstance(MAIL_SERVICE_INSTANCE_ID)).thenReturn(Mono.just(serviceInstance));

        // when get service instance
        GetServiceInstanceRequest request = GetServiceInstanceRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .build();

        // then the existing service instance is retrieved
        StepVerifier.create(mailServiceInstanceService.getServiceInstance(request))
            .consumeNextWith(response -> {
                assertEquals(MAIL_SERVICE_DEFINITION_ID, response.getServiceDefinitionId());
                assertEquals(MAIL_SERVICE_PLAN_ID, response.getPlanId());
                assertEquals(MAIL_DASHBOARD_URL, response.getDashboardUrl());
            })
            .verifyComplete();
    }
}
