package datajpa.domain.domain6;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 每个类层次结构一张表
 */
@Entity
@DiscriminatorValue("CC")
public class CreditCard3 extends BillingDetails3 {


    @Column(name = "CC_NUMBER")
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
