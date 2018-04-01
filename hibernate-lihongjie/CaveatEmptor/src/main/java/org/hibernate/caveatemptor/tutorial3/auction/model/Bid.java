package org.hibernate.caveatemptor.tutorial3.auction.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * An immutable class representing one bid.
 * <p>
 * If the "successful" property is used in a legacy situation
 * (see book chapter 8), it is no longer an immutable class with
 * consequences for second-level caching.
 * <p>
 * Note: This legacy mapping isn't currently possible with annotations.
 *
 * @see Item
 * @see User
 * @author Christian Bauer
 */
@Entity
@Table(name = "BID")
@org.hibernate.annotations.Entity(mutable = false)
public class Bid implements Serializable, Comparable {

    @Id @GeneratedValue
    @Column(name = "BID_ID")
    private Long id = null;

    @org.hibernate.annotations.Type(type = "monetary_amount_usd")
    @org.hibernate.annotations.Columns( columns = {
			@Column( name="BID_AMOUNT", length = 2, updatable = false),
			@Column( name="BID_AMOUNT_CURRENCY", length = 3, updatable = false)
        }
	)
    private MonetaryAmount amount;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false, updatable = false, insertable = false)
    @org.hibernate.annotations.ForeignKey(name="FK_ITEM_ID")
	private Item item;

    @ManyToOne
    @JoinColumn(name = "BIDDER_ID", nullable = false, updatable = false)
    @org.hibernate.annotations.ForeignKey(name="FK_BIDDER_ID")
    private User bidder;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false, updatable = false)
	private Date created = new Date();

    // TODO: This can't be mapped in annotations, there is no <properties> grouping
    @Transient
    private boolean successful = false;

    /**
	 * No-arg constructor for JavaBean tools
	 */
	public Bid() {}

	/**
	 * Full constructor
	 *
	 * @param amount
	 * @param item
	 * @param bidder
	 */

	public Bid(MonetaryAmount amount, Item item, User bidder) {
		this.amount = amount;
		this.item = item;
		this.bidder = bidder;
	}

	// ********************** Accessor Methods ********************** //

	public Long getId() { return id; }

	public MonetaryAmount getAmount() { return amount; }

	public Item getItem() { return item; }

	public User getBidder() { return bidder; }

	public Date getCreated() { return created; }

    public boolean isSuccessful() { return successful; }
    public void setSuccessful(boolean successful) { this.successful = successful; }

    // ********************** Common Methods ********************** //

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Bid)) return false;

		final Bid bid = (Bid) o;

        if (! getItem().getId().equals(bid.getItem().getId())) return false;
        if (! (created.getTime() == bid.created.getTime()) ) return false;

        if (!amount.equals(bid.amount)) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = amount.hashCode();
		result = 29 * result + created.hashCode();
		return result;
	}

	public String toString() {
		return  "Bid ('" + getId() + "'), " +
				"Created: '" + getCreated() + "' " +
				"Amount: '" + getAmount() + "'";
	}

	public int compareTo(Object o) {
		if (o instanceof Bid) {
            // Don't compare Date objects! Use the time in milliseconds!
            return Long.valueOf(this.getCreated().getTime()).compareTo(
                    Long.valueOf( ((Bid)o).getCreated().getTime())
                   );
		}
		return 0;
	}

	// ********************** Business Methods ********************** //

}