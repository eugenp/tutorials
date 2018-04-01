package datajpa.domain.domain6;

import javax.persistence.*;

/**
 * 每个带有隐式多态的具体类一张表
 */
@Entity
@AttributeOverride(name = "owner", column = @Column(name = "CC_OWNER", nullable = false))
public class CreditCard1 extends BillingDetails1 {

    @Id
    @GeneratedValue
    @Column(name = "CREADIT_CARD_ID")
    private Long id;

    @Column(name = "NUMBER", nullable = false)
    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
