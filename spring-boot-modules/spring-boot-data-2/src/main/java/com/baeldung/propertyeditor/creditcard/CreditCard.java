package com.baeldung.propertyeditor.creditcard;

public class CreditCard {

    private String rawCardNumber;
    
    private Integer bankIdNo;
    
    private Integer accountNo;
    
    private Integer checkCode;
    
    public String getRawCardNumber() {
        return rawCardNumber;
    }
    public void setRawCardNumber(String rawCardNumber) {
        this.rawCardNumber = rawCardNumber;
    }
    
    public Integer getBankIdNo() {
        return bankIdNo;
    }
    public void setBankIdNo(Integer bankIdNo) {
        this.bankIdNo = bankIdNo;
    }
    
    public Integer getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }
    
    public Integer getCheckCode() {
        return checkCode;
    }
    public void setCheckCode(Integer checkCode) {
        this.checkCode = checkCode;
    }
    
}
