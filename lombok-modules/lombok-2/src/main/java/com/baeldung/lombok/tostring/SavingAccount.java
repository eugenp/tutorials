package com.baeldung.lombok.tostring;

import lombok.ToString;

@ToString(callSuper = true)
public class SavingAccount extends Account {

    private String savingAccountId;

    public String getSavingAccountId() {
        return savingAccountId;
    }

    public void setSavingAccountId(String savingAccountId) {
        this.savingAccountId = savingAccountId;
    }
}
