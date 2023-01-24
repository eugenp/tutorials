package com.baeldung.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.credhub.core.CredHubOperations;
import org.springframework.credhub.core.credential.CredHubCredentialOperations;
import org.springframework.credhub.core.permissionV2.CredHubPermissionV2Operations;
import org.springframework.credhub.support.CredentialDetails;
import org.springframework.credhub.support.CredentialPermission;
import org.springframework.credhub.support.CredentialRequest;
import org.springframework.credhub.support.SimpleCredentialName;
import org.springframework.credhub.support.certificate.CertificateCredential;
import org.springframework.credhub.support.certificate.CertificateCredentialRequest;
import org.springframework.credhub.support.json.JsonCredentialRequest;
import org.springframework.credhub.support.password.PasswordCredential;
import org.springframework.credhub.support.password.PasswordCredentialRequest;
import org.springframework.credhub.support.password.PasswordParameters;
import org.springframework.credhub.support.password.PasswordParametersRequest;
import org.springframework.credhub.support.permissions.Operation;
import org.springframework.credhub.support.permissions.Permission;
import org.springframework.credhub.support.rsa.RsaCredential;
import org.springframework.credhub.support.rsa.RsaCredentialRequest;
import org.springframework.credhub.support.ssh.SshCredential;
import org.springframework.credhub.support.ssh.SshCredentialRequest;
import org.springframework.credhub.support.user.UserCredential;
import org.springframework.credhub.support.user.UserCredentialRequest;
import org.springframework.credhub.support.value.ValueCredential;
import org.springframework.credhub.support.value.ValueCredentialRequest;

import com.baeldung.model.Credential;

public class CredentialService {

    private final CredHubCredentialOperations credentialOperations;
    private final CredHubPermissionV2Operations permissionOperations;

    public CredentialService(CredHubOperations credHubOperations) {
        this.credentialOperations = credHubOperations.credentials();
        this.permissionOperations = credHubOperations.permissionsV2();
    }

    public String generatePassword(String name) {
        try {
            SimpleCredentialName credentialName = new SimpleCredentialName(name);
            PasswordParameters parameters = PasswordParameters.builder()
              .length(24)
              .excludeUpper(false)
              .excludeLower(false)
              .includeSpecial(true)
              .excludeNumber(false)
              .build();

            CredentialDetails<PasswordCredential> generatedCred = credentialOperations.generate(PasswordParametersRequest.builder()
              .name(credentialName)
              .parameters(parameters)
              .build());

            return generatedCred.getValue()
              .getPassword();
        } catch (Exception e) {
            return null;
        }
    }

    public String writeCredential(Credential credential) {
        try {
            SimpleCredentialName credentialName = new SimpleCredentialName(credential.getName());
            CredentialRequest request = null;
            Map<String, Object> value = credential.getValue();
            switch (credential.getType()) {
            case "value":
                ValueCredential valueCredential = new ValueCredential((String) value.get("value"));
                request = ValueCredentialRequest.builder()
                  .name(credentialName)
                  .value(valueCredential)
                  .build();
                break;

            case "json":
                request = JsonCredentialRequest.builder()
                  .name(credentialName)
                  .value(value)
                  .build();
                break;

            case "user":
                UserCredential userCredential = new UserCredential((String) value.get("username"), (String) value.get("password"));
                request = UserCredentialRequest.builder()
                  .name(credentialName)
                  .value(userCredential)
                  .build();
                break;

            case "password":
                PasswordCredential passwordCredential = new PasswordCredential((String) value.get("password"));
                request = PasswordCredentialRequest.builder()
                  .name(credentialName)
                  .value(passwordCredential)
                  .build();
                break;

            case "certificate":
                CertificateCredential certificateCredential = new CertificateCredential((String) value.get("certificate"), (String) value.get("certificate_authority"), (String) value.get("private_key"));
                request = CertificateCredentialRequest.builder()
                  .name(credentialName)
                  .value(certificateCredential)
                  .build();
                break;

            case "rsa":
                RsaCredential rsaCredential = new RsaCredential((String) value.get("public_key"), (String) value.get("private_key"));
                request = RsaCredentialRequest.builder()
                  .name(credentialName)
                  .value(rsaCredential)
                  .build();
                break;

            case "ssh":
                SshCredential sshCredential = new SshCredential((String) value.get("public_key"), (String) value.get("private_key"));
                request = SshCredentialRequest.builder()
                  .name(credentialName)
                  .value(sshCredential)
                  .build();
                break;

            default:
            }
            if (request != null) {
                credentialOperations.write(request);
            }
            return "Credential:" + credentialName + " written successfully!";
        } catch (Exception e) {
            return "Error! Unable to write credential";
        }
    }

    public String rotatePassword(String name) {
        try {
            SimpleCredentialName credentialName = new SimpleCredentialName(name);
            CredentialDetails<PasswordCredential> oldPassword = credentialOperations.getByName(credentialName, PasswordCredential.class);
            CredentialDetails<PasswordCredential> newPassword = credentialOperations.regenerate(credentialName, PasswordCredential.class);

            return "Credential:" + credentialName + " re-generated successfully!";
        } catch (Exception e) {
            return "Error! Unable to re-generate credential";
        }
    }

    public String deletePassword(String name) {
        try {
            SimpleCredentialName credentialName = new SimpleCredentialName(name);
            credentialOperations.deleteByName(credentialName);
            return "Credential:" + credentialName + " deleted successfully!";
        } catch (Exception e) {
            return "Error! Unable to delete credential";
        }
    }

    public String getPassword(String name) {
        try {
            SimpleCredentialName credentialName = new SimpleCredentialName(name);
            return credentialOperations.getByName(credentialName, PasswordCredential.class)
              .getValue()
              .getPassword();
        } catch (Exception e) {
            return null;
        }
    }

    public CredentialPermission addCredentialPermission(String name) {
        SimpleCredentialName credentialName = new SimpleCredentialName(name);
        try {
            Permission permission = Permission.builder()
              .app(UUID.randomUUID()
                .toString())
              .operations(Operation.READ, Operation.WRITE)
              .user("u101")
              .build();
            CredentialPermission credentialPermission = permissionOperations.addPermissions(credentialName, permission);
            return credentialPermission;
        } catch (Exception e) {
            return null;
        }
    }

    public CredentialPermission getCredentialPermission(String name) {
        try {
            return permissionOperations.getPermissions(name);
        } catch (Exception e) {
            return null;
        }
    }
}

