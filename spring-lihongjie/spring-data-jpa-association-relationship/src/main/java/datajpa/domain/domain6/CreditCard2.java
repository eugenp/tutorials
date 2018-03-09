package datajpa.domain.domain6;

import javax.persistence.*;

/**
 * 每个带有隐式多态的具体类一张表
 */
@Entity
@AttributeOverride(name = "owner", column = @Column(name = "CC_OWNER", nullable = false))
public class CreditCard2 extends BillingDetails2 {


    @Column(name = "NUMBER", nullable = false)
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
