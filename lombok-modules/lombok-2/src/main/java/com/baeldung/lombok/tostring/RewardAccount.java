package com.baeldung.lombok.tostring;

import lombok.ToString;

@ToString
public class RewardAccount extends Account {

    private String rewardAccountId;

    private Object[] relatedAccounts;

    public String getRewardAccountId() {
        return rewardAccountId;
    }

    public void setRewardAccountId(String rewardAccountId) {
        this.rewardAccountId = rewardAccountId;
    }

    public Object[] getRelatedAccounts() {
        return relatedAccounts;
    }

    public void setRelatedAccounts(Object[] relatedAccounts) {
        this.relatedAccounts = relatedAccounts;
    }
}
