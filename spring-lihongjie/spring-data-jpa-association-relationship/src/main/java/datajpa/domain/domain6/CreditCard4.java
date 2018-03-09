package datajpa.domain.domain6;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * 每个子类一张表
 */
@Entity
@PrimaryKeyJoinColumn(name = "CREDIT_CARD_ID")
public class CreditCard4 extends BillingDetails4 {


    @Column(name = "CC_NUMBER")
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "CreditCard4{" +
                "number='" + number + '\'' +
                "id='" + getId() + '\'' +
                "owner='" + getOwner() + '\'' +
                '}';
    }
}
