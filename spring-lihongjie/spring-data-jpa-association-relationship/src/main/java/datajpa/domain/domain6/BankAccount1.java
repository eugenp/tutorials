package datajpa.domain.domain6;

import javax.persistence.*;

/**
 * This billing strategy uses a simple bank account.
 *
 * @author Christian Bauer
 */
@Entity
@AttributeOverride(name = "owner", column = @Column(name = "BANK_OWNER", nullable = false))
public class BankAccount1 extends BillingDetails1 {

    @Id
    @GeneratedValue
    @Column(name = "BANK_ACCOUNT_ID")
    private Long id;

    @Column(name = "BA_ACCOUNT", nullable = true, length = 16)
    private String account;

    @Column(name = "BA_BANKNAME", nullable = true, length = 255)
    private String bankname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
