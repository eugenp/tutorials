package com.baeldung.tutorials.passkey.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table(name = "PASSKEY_CREDENTIALS")
public class PasskeyCredential {
    @Id
    @Column(value = "ID")
    public Long id;

    @Column(value = "USER_ID")
    public Long userId;

    @Column(value = "LABEL")
    public String label;

    @Column(value = "CREDENTIAL_TYPE")
    public String credentialType;

    @Column(value = "CREDENTIAL_ID")
    public String credentialId;

    @Column(value = "PUBLIC_KEY_COSE")
    public String publicKeyCose;

    @Column(value = "SIGNATURE_COUNT")
    public Long signatureCount;

    @Column(value = "UV_INITIALIZED")
    public Boolean uvInitialized;

    @Column(value = "TRANSPORTS")
    public String transports;

    @Column(value = "BACKUP_ELIGIBLE")
    public Boolean backupEligible;

    @Column(value = "BACKUP_STATE")
    public Boolean backupState;

    @Column(value = "ATTESTATION_OBJECT")
    public String attestationObject;

    @Column(value = "LAST_USED")
    public Instant lastUsed;

    @Column(value = "CREATED")
    public Instant created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AggregateReference<PasskeyUser, Long> getUser() {
        return AggregateReference.to(userId);
    }

    public void setUser(AggregateReference<PasskeyUser, Long> userId) {
        this.userId = userId.getId();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getPublicKeyCose() {
        return publicKeyCose;
    }

    public void setPublicKeyCose(String publicKeyCose) {
        this.publicKeyCose = publicKeyCose;
    }

    public Long getSignatureCount() {
        return signatureCount;
    }

    public void setSignatureCount(Long signatureCount) {
        this.signatureCount = signatureCount;
    }

    public Boolean getUvInitialized() {
        return uvInitialized;
    }

    public void setUvInitialized(Boolean uvInitialized) {
        this.uvInitialized = uvInitialized;
    }

    public String getTransports() {
        return transports;
    }

    public void setTransports(String transports) {
        this.transports = transports;
    }

    public Boolean getBackupEligible() {
        return backupEligible;
    }

    public void setBackupEligible(Boolean backupEligible) {
        this.backupEligible = backupEligible;
    }

    public Boolean getBackupState() {
        return backupState;
    }

    public void setBackupState(Boolean backupState) {
        this.backupState = backupState;
    }

    public String getAttestationObject() {
        return attestationObject;
    }

    public void setAttestationObject(String attestationObject) {
        this.attestationObject = attestationObject;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

}