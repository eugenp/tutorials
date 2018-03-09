package datajpa.domain.domain6;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 每个带有隐式多态的具体类一张表
 *
 */
@MappedSuperclass
public abstract class BillingDetails1 {

    @Column(name = "OWNER", nullable = false)
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
