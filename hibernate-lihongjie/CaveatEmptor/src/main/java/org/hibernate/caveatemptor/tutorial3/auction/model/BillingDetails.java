package org.hibernate.caveatemptor.tutorial3.auction.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This is the abstract superclass for BillingDetails.
 * <p>
 * A BillingDetails object is always associated with a single
 * User and depends on the lifecycle of that user. It represents
 * one of the billing strategies the User has choosen, usually
 * one BillingDetails is the default in a collection of many.
 *
 * @author Christian Bauer
 */
@Entity
@Table(name = "BILLING_DETAILS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "BILLING_DETAILS_TYPE",
    discriminatorType = DiscriminatorType.STRING
)
public abstract class BillingDetails implements Serializable, Comparable {

    @Id @GeneratedValue
    @Column(name = "BILLING_DETAILS_ID")
    private Long id = null;

    @Version
    @Column(name = "OBJ_VERSION")
    private int version = 0;

    @Column(name = "OWNER", nullable = false)
    private String owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", updatable = false)
    @org.hibernate.annotations.ForeignKey(name = "FK_USER_ID")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false, updatable = false)
	private Date created = new Date();

	/**
	 * No-arg constructor for JavaBean tools
	 */
	public BillingDetails() {}


    /**
     * Full constructor
     */
    protected BillingDetails(String owner, User user) {
        this.owner = owner;
        this.user = user;
    }

    // ********************** Accessor Methods ********************** //

    public Long getId() { return id; }
    public int getVersion() { return version; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public User getUser() { return user; }

	public Date getCreated() { return created; }

	// ********************** Common Methods ********************** //

    // TODO: This is not a very good equals() implementation
    public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BillingDetails)) return false;

		final BillingDetails billingDetails = (BillingDetails) o;

        if (! (created.getTime() == billingDetails.created.getTime()) ) return false;
		if (!getOwner().equals(billingDetails.getOwner())) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = getCreated().hashCode();
		result = 29 * result + getOwner().hashCode();
		return result;
	}

	public int compareTo(Object o) {
		if (o instanceof BillingDetails)
            // Don't compare Date objects! Use the time in milliseconds!
            return Long.valueOf(this.getCreated().getTime()).compareTo(
                    Long.valueOf( ((BillingDetails)o).getCreated().getTime())
                   );
		return 0;
	}

	// ********************** Business Methods ********************** //

	/**
	 * Checks if the billing information is correct.
	 * <p>
	 * Check algorithm is implemented in subclasses.
	 *
	 * @return boolean
	 */
	public abstract boolean isValid();

}
