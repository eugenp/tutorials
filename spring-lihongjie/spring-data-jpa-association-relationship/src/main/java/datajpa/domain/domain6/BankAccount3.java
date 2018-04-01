package datajpa.domain.domain6;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 每个类层次结构一张表
 */
@Entity
@DiscriminatorValue("BA")
public class BankAccount3 extends BillingDetails3 {


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
}
