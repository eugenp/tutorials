package com.baeldung.mdc;

public class Transfer {

    private String transactionId;
    private String sender;
    private Long amount;
    private String accountId;
    private boolean investmentFund;
    private String investmentFundId;

    public Transfer(String transactionId, String sender, Long amount, String accountId, boolean investmentFund, String investmentFundId) {
        this.transactionId = transactionId;
        this.sender = sender;
        this.amount = amount;
        this.accountId = accountId;
        this.investmentFund = investmentFund;
        this.investmentFundId = investmentFundId;
    }

    public String getSender() {
        return sender;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Long getAmount() {
        return amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public boolean isInvestmentFund() {
        return investmentFund;
    }

    public String getInvestmentFundId() {
        return investmentFundId;
    }

}
