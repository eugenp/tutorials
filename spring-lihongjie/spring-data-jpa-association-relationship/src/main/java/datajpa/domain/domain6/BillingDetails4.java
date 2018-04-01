package datajpa.domain.domain6;

import javax.persistence.*;

/**
 * 每个子类一张表
 *
 *
 **/
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetails4 {

    @Id
    @GeneratedValue
    @Column(name = "BILLING_DETAILS_ID")
    private Long id;

    @Column(name = "OWNER", nullable = false)
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
