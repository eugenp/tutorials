package datajpa.domain.domain6;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 每个子类一张表
 */
@Entity
public class BankAccount4 extends BillingDetails4 {


    @Column(name = "BA_ACCOUNT", length = 16)
    private String account;

    @Column(name = "BA_BANKNAME", length = 255)
    private String bankname;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    @Override
    public String toString() {
        return "BankAccount4{" +
                "account='" + account + '\'' +
                ", bankname='" + bankname + '\'' +
                '}';
    }
}
