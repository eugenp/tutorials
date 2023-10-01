package com.baeldung.expression.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class License {

    private UUID id;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private boolean active;

    private boolean renewalRequired;

    private LicenseType licenseType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRenewalRequired() {
        return renewalRequired;
    }

    public void setRenewalRequired(boolean renewalRequired) {
        this.renewalRequired = renewalRequired;
    }

    public enum LicenseType {
        INDIVIDUAL, FAMILY
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }
}