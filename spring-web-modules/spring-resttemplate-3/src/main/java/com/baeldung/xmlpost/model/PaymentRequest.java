package com.baeldung.xmlpost.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "PaymentRequest")
public class PaymentRequest {

    @JacksonXmlProperty(localName = "transactionId")
    private String transactionId;

    @JacksonXmlProperty(localName = "amount")
    private Double amount;

    @JacksonXmlProperty(localName = "currency")
    private String currency;

    @JacksonXmlProperty(localName = "recipient")
    private String recipient;

    public PaymentRequest() {
    }

    public PaymentRequest(String transactionId, Double amount, String currency, String recipient) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.recipient = recipient;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
