package com.baeldung.context.entity;

public class SecurityDetail {
    private String securityID;
    private String securityName;
    private String securityType;
    private String exchangeId;

    public SecurityDetail(String securityID, String securityName, String securityType, String exchangeId) {
        this.securityID = securityID;
        this.securityName = securityName;
        this.securityType = securityType;
        this.exchangeId = exchangeId;
    }

    public String getSecurityID() {
        return securityID;
    }

    public void setSecurityID(String securityID) {
        this.securityID = securityID;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }
}
