package com.baeldung.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.DateUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.credhub.support.CredentialPermission;
import org.springframework.credhub.support.permissions.Operation;

import com.baeldung.model.Credential;
import com.baeldung.service.CredentialService;

@Ignore
@ExtendWith(MockitoExtension.class)
public class CredentialServiceUnitTest {

    @InjectMocks
    private CredentialService credentialService;

    @Test
    public void whenGeneratePassword_thenReturnNewPassword() {
        String orderApiKey = credentialService.generatePassword("order_api_key");
        assertFalse(orderApiKey.isEmpty());
    }

    @Test
    public void whenWriteCredential_thenReturnSuccess() {
        Map<String, Object> value = new HashMap<>();
        value.put("end_date", DateUtil.now());
        value.put("start_date", DateUtil.yesterday());

        Credential credential = new Credential();
        credential.setName("order_config_json");
        credential.setType("json");
        credential.setValue(value);

        String result = credentialService.writeCredential(credential);
        assertThat(result).isEqualTo("Credential:order_config_json written successfully!");
    }

    @Test
    public void whenRotatePassword_thenRegenerateNewPassword() {
        String orderApiKey = credentialService.rotatePassword("order_api_key");
        assertThat(orderApiKey).isEqualTo("Credential:order_api_key re-generated successfully!");
    }

    @Test
    public void whenRevokePassword_thenDeletePassword() {
        String orderApiKey = credentialService.deletePassword("order_api_key");
        assertThat(orderApiKey).isEqualTo("Credential:order_api_key deleted successfully!");
    }

    @Test
    public void whenRetrieveExistingCredential_thenReturnCredentialValue() {
        String orderConfigJson = credentialService.getPassword("order_config_json");
        assertFalse(orderConfigJson.isEmpty());
    }

    @Test
    public void whenCredentialPermissionCreated_thenAddToCredential() {
        CredentialPermission orderConfig = credentialService.addCredentialPermission("order_config_json");
        List<Operation> operations = orderConfig.getPermission()
          .getOperations();
        String identity = orderConfig.getPermission()
          .getActor()
          .getIdentity();

        CredentialPermission newOrderConfig = credentialService.getCredentialPermission("order_config_json");
        List<Operation> newOperations = newOrderConfig.getPermission()
          .getOperations();
        String newIdentity = newOrderConfig.getPermission()
          .getActor()
          .getIdentity();

        assertThat(operations.size() == newOperations.size() && operations.containsAll(newOperations) && newOperations.containsAll(operations));
        assertThat(identity).isEqualTo(newIdentity);
    }
}
