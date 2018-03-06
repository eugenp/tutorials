package datajpa.domain.domain6;

import javax.persistence.*;

/**
 * This billing strategy uses a simple bank account.
 *
 * @author Christian Bauer
 */
@Entity
@AttributeOverride(name = "owner", column = @Column(name = "BANK_OWNER", nullable = false))
public class BankAccount2 extends BillingDetails2 {


    @Column(name = "BA_ACCOUNT", nullable = true, length = 16)
    private String account;

    @Column(name = "BA_BANKNAME", nullable = true, length = 255)
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
