package com.baeldung.ndc;

public class Investment {
    private String transactionId;
    private String owner;
    private Long amount;
    private String savingsAccountId;
    private boolean investmentFund;
    private String investmentFundId;

    public Investment(String transactionId, String owner, Long amount, String savingsAccountId, boolean investmentFund, String investmentFundId) {
        this.transactionId = transactionId;
        this.owner = owner;
        this.amount = amount;
        this.savingsAccountId = savingsAccountId;
        this.investmentFund = investmentFund;
        this.investmentFundId = investmentFundId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getSavingsAccountId() {
        return savingsAccountId;
    }

    public void setSavingsAccountId(String savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
    }

    public boolean isInvestmentFund() {
        return investmentFund;
    }

    public void setInvestmentFund(boolean investmentFund) {
        this.investmentFund = investmentFund;
    }

    public String getInvestmentFundId() {
        return investmentFundId;
    }

    public void setInvestmentFundId(String investmentFundId) {
        this.investmentFundId = investmentFundId;
    }
}
