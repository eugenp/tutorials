package datajpa.domain.domain6;

import javax.persistence.*;

/**
 * 每个带有联合的具体类一张表
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BillingDetails2 {

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
