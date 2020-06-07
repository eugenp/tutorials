package com.baeldung.spring.cloud.openservicebroker.services;

import com.baeldung.spring.cloud.openservicebroker.mail.MailService;
import com.baeldung.spring.cloud.openservicebroker.mail.MailServiceBinding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceBindingRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

import static com.baeldung.spring.cloud.openservicebroker.mail.MailService.PASSWORD_KEY;
import static com.baeldung.spring.cloud.openservicebroker.mail.MailService.URI_KEY;
import static com.baeldung.spring.cloud.openservicebroker.mail.MailService.USERNAME_KEY;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MailServiceInstanceBindingServiceUnitTest {

    private static final String MAIL_SERVICE_INSTANCE_ID = "test@baeldung.com";
    private static final String MAIL_SERVICE_BINDING_ID = "test";
    private static final String MAIL_SYSTEM_URL = "http://localhost:8080/mail-system/test@baeldung.com";

    @Mock
    private MailService mailService;

    private MailServiceInstanceBindingService mailServiceInstanceBindingService;

    @BeforeEach
    public void setUp() {
        initMocks(this);

        this.mailServiceInstanceBindingService = new MailServiceInstanceBindingService(mailService);
    }

    @Test
    public void givenServiceBindingDoesNotExist_whenCreateServiceBinding_thenNewBindingIsCreated() {
        // given service binding does not exist
        when(mailService.serviceBindingExists(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_BINDING_ID)).thenReturn(Mono.just(false));

        Map<String, Object> credentials = generateCredentials();
        MailServiceBinding serviceBinding = new MailServiceBinding(MAIL_SERVICE_BINDING_ID, credentials);
        when(mailService.createServiceBinding(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_BINDING_ID))
            .thenReturn(Mono.just(serviceBinding));

        // when create service binding
        CreateServiceInstanceBindingRequest request = CreateServiceInstanceBindingRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .bindingId(MAIL_SERVICE_BINDING_ID)
            .build();

        // then a new service binding is provisioned
        StepVerifier.create(mailServiceInstanceBindingService.createServiceInstanceBinding(request))
            .consumeNextWith(response -> {
                assertTrue(response instanceof CreateServiceInstanceAppBindingResponse);
                CreateServiceInstanceAppBindingResponse bindingResponse = (CreateServiceInstanceAppBindingResponse) response;
                assertFalse(bindingResponse.isBindingExisted());
                validateBindingCredentials(bindingResponse.getCredentials());
            })
            .verifyComplete();
    }

    @Test
    public void givenServiceBindingExists_whenCreateServiceBinding_thenExistingBindingIsRetrieved() {
        // given service binding exists
        when(mailService.serviceBindingExists(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_BINDING_ID)).thenReturn(Mono.just(true));

        Map<String, Object> credentials = generateCredentials();
        MailServiceBinding serviceBinding = new MailServiceBinding(MAIL_SERVICE_BINDING_ID, credentials);
        when(mailService.getServiceBinding(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_BINDING_ID))
            .thenReturn(Mono.just(serviceBinding));

        // when create service binding
        CreateServiceInstanceBindingRequest request = CreateServiceInstanceBindingRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .bindingId(MAIL_SERVICE_BINDING_ID)
            .build();

        // then a new service binding is provisioned
        StepVerifier.create(mailServiceInstanceBindingService.createServiceInstanceBinding(request))
            .consumeNextWith(response -> {
                assertTrue(response instanceof CreateServiceInstanceAppBindingResponse);
                CreateServiceInstanceAppBindingResponse bindingResponse = (CreateServiceInstanceAppBindingResponse) response;
                assertTrue(bindingResponse.isBindingExisted());
                validateBindingCredentials(bindingResponse.getCredentials());
            })
            .verifyComplete();
    }

    @Test
    public void givenServiceBindingDoesNotExist_whenGetServiceBinding_thenException() {
        // given service binding does not exist
        when(mailService.getServiceBinding(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_BINDING_ID)).thenReturn(Mono.empty());

        // when get service binding
        GetServiceInstanceBindingRequest request = GetServiceInstanceBindingRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .bindingId(MAIL_SERVICE_BINDING_ID)
            .build();

        // then ServiceInstanceBindingDoesNotExistException is thrown
        StepVerifier.create(mailServiceInstanceBindingService.getServiceInstanceBinding(request))
            .expectErrorMatches(ex -> ex instanceof ServiceInstanceBindingDoesNotExistException)
            .verify();
    }

    @Test
    public void givenServiceBindingExists_whenGetServiceBinding_thenExistingBindingIsRetrieved() {
        // given service binding exists
        Map<String, Object> credentials = generateCredentials();
        MailServiceBinding serviceBinding = new MailServiceBinding(MAIL_SERVICE_BINDING_ID, credentials);
        when(mailService.getServiceBinding(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_BINDING_ID))
            .thenReturn(Mono.just(serviceBinding));

        // when get service binding
        GetServiceInstanceBindingRequest request = GetServiceInstanceBindingRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .bindingId(MAIL_SERVICE_BINDING_ID)
            .build();

        // then the existing service binding is retrieved
        StepVerifier.create(mailServiceInstanceBindingService.getServiceInstanceBinding(request))
            .consumeNextWith(response -> {
                assertTrue(response instanceof GetServiceInstanceAppBindingResponse);
                GetServiceInstanceAppBindingResponse bindingResponse = (GetServiceInstanceAppBindingResponse) response;
                validateBindingCredentials(bindingResponse.getCredentials());
            })
            .verifyComplete();
    }

    @Test
    public void givenServiceBindingDoesNotExist_whenDeleteServiceBinding_thenException() {
        // given service binding does not exist
        when(mailService.serviceBindingExists(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_BINDING_ID)).thenReturn(Mono.just(false));

        // when delete service binding
        DeleteServiceInstanceBindingRequest request = DeleteServiceInstanceBindingRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .bindingId(MAIL_SERVICE_BINDING_ID)
            .build();

        // then ServiceInstanceBindingDoesNotExistException is thrown
        StepVerifier.create(mailServiceInstanceBindingService.deleteServiceInstanceBinding(request))
            .expectErrorMatches(ex -> ex instanceof ServiceInstanceBindingDoesNotExistException)
            .verify();
    }

    @Test
    public void givenServiceBindingExists_whenDeleteServiceBinding_thenExistingBindingIsDeleted() {
        // given service binding exists
        when(mailService.serviceBindingExists(MAIL_SERVICE_INSTANCE_ID, MAIL_SERVICE_BINDING_ID)).thenReturn(Mono.just(true));
        when(mailService.deleteServiceBinding(MAIL_SERVICE_INSTANCE_ID)).thenReturn(Mono.empty());

        // when delete service binding
        DeleteServiceInstanceBindingRequest request = DeleteServiceInstanceBindingRequest.builder()
            .serviceInstanceId(MAIL_SERVICE_INSTANCE_ID)
            .bindingId(MAIL_SERVICE_BINDING_ID)
            .build();

        // then the existing service binding is retrieved
        StepVerifier.create(mailServiceInstanceBindingService.deleteServiceInstanceBinding(request))
            .consumeNextWith(response -> {
                assertFalse(response.isAsync());
                assertNull(response.getOperation());
            })
            .verifyComplete();
    }

    private void validateBindingCredentials(Map<String, Object> bindingCredentials) {
        assertNotNull(bindingCredentials);
        assertEquals(3, bindingCredentials.size());
        assertTrue(bindingCredentials.containsKey(URI_KEY));
        assertTrue(bindingCredentials.containsKey(USERNAME_KEY));
        assertTrue(bindingCredentials.containsKey(PASSWORD_KEY));
        assertEquals(MAIL_SYSTEM_URL, bindingCredentials.get(URI_KEY));
        assertEquals(MAIL_SERVICE_BINDING_ID, bindingCredentials.get(USERNAME_KEY));
        assertNotNull(bindingCredentials.get(PASSWORD_KEY));
    }

    private Map<String, Object> generateCredentials() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put(URI_KEY, MAIL_SYSTEM_URL);
        credentials.put(USERNAME_KEY, MAIL_SERVICE_BINDING_ID);
        credentials.put(PASSWORD_KEY, randomUUID().toString());
        return credentials;
    }
}
