package datajpa.domain.domain4;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lihongjie on 3/31/17.
 */
@Entity(name = "Bid_4")
public class Bid {
    @Id
    @GeneratedValue
    @Column(name = "BID_ID")
    private Long id;

    @Column(name = "AMOUNT")
    private Float amount;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @ManyToOne(optional=false)
    @JoinColumn(name="ITEM_ID", nullable=false, updatable=false)
    private Item item;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
