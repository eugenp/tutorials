package org.hibernate.caveatemptor.tutorial3.auction.model;

import auction.exceptions.BusinessException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * A user of the CaveatEmptor auction application.
 *
 * @author Christian Bauer
 */
@Entity
@Table(name = "USERS")
@SecondaryTable(
    name = "BILLING_ADDRESS",
    pkJoinColumns = {
        @PrimaryKeyJoinColumn(name="USER_ID")
    }
)
@org.hibernate.annotations.BatchSize(size = 10)
public class User implements Serializable, Comparable {

    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id = null;

    @Version
    @Column(name = "OBJ_VERSION")
    private int version = 0;

    @Column(name = "FIRSTNAME", length = 255, nullable = false)
    private String firstname;

    @Column(name = "LASTNAME", length = 255, nullable = false)
    private String lastname;

    @Column(name = "USERNAME", length = 16, nullable = false, unique = true)
    //@org.hibernate.annotations.Check( constraints = "regexp_like(USERNAME,'^[[:alpha:]]+$')" )
    private String username; // Unique and immutable

    @Column(name = "`PASSWORD`", length = 12, nullable = false)
    private String password;

    @Column(name = "EMAIL", length = 255, nullable = false)
    private String email;

    @Column(name = "RANK", nullable = false)
    private int ranking = 0;

    @Column(name = "IS_ADMIN", nullable = false)
    private boolean admin = false;

    @Embedded
    @AttributeOverrides( {
        @AttributeOverride(name   = "street",
                           column = @Column(name="HOME_STREET", length = 255) ),
        @AttributeOverride(name   = "zipcode",
                           column = @Column(name="HOME_ZIPCODE", length = 16) ),
        @AttributeOverride(name   = "city",
                           column = @Column(name="HOME_CITY", length = 255) )
        })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides( {
        @AttributeOverride(
            name   = "street",
            column = @Column(name="STREET", length = 255,
                             table = "BILLING_ADDRESS")
        ),
        @AttributeOverride(
            name   = "zipcode",
            column = @Column(name="ZIPCODE", length = 16,
                             table = "BILLING_ADDRESS")
        ),
        @AttributeOverride(
            name   = "city",
            column = @Column(name="CITY", length = 255,
                             table = "BILLING_ADDRESS")
        )
    })
    // TODO: This is ignored: @org.hibernate.annotations.ForeignKey(name = "FK_BILLING_ADDRESS_ID")
    private Address billingAddress;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @PrimaryKeyJoinColumn
    private AddressEntity shippingAddress;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user")
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<BillingDetails> billingDetails = new HashSet<BillingDetails>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DEFAULT_BILLING_DETAILS_ID", nullable = true)
    @org.hibernate.annotations.ForeignKey(name = "FK_DEFAULT_BILLING_DETAILS_ID")
    private BillingDetails defaultBillingDetails;

    @OneToMany(mappedBy = "seller")
    private Collection<Item> itemsForSale = new ArrayList<Item>();

    @OneToMany(mappedBy = "buyer")
    private Set<Item> boughtItems = new HashSet<Item>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false, updatable = false)
    private Date created = new Date();

    /**
     * No-arg constructor for JavaBean tools
     */
    public User() {}

    /**
     * Full constructor
     */
    public User(String firstname, String lastname, String username, String password, String email, int ranking,
                boolean admin, Address homeAddress, Address billingAddress, AddressEntity shippingAddress,
                Set<BillingDetails> billingDetails, BillingDetails defaultBillingDetails,
                Set<Item> itemsForSale, Set<Item> boughtItems) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.ranking = ranking;
        this.admin = admin;
        this.homeAddress = homeAddress;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.billingDetails = billingDetails;
        this.defaultBillingDetails = defaultBillingDetails;
        this.itemsForSale = itemsForSale;
        this.boughtItems = boughtItems;
    }

    /**
     * Simple constructor.
     */
    public User(String firstname, String lastname,
                String username, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // ********************** Accessor Methods ********************** //

    public Long getId() { return id; }
    public int getVersion() { return version; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getRanking() { return ranking; }
    public void setRanking(int ranking) { this.ranking = ranking; }

    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }

    public Address getHomeAddress() { return homeAddress; }
    public void setHomeAddress(Address homeAddress) { this.homeAddress = homeAddress; }

    public Address getBillingAddress() { return billingAddress; }
    public void setBillingAddress(Address billingAddress) { this.billingAddress = billingAddress; }

    public AddressEntity getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(AddressEntity shippingAddress) { this.shippingAddress = shippingAddress; }

    public Set getBillingDetails() { return billingDetails; }
    /**
      * Adds a <tt>BillingDetails</tt> to the set.
      * <p>
      * This method checks if there is only one billing method
      * in the set, then makes this the default.
      *
      * @param billingDetails
      */
     public void addBillingDetails(BillingDetails billingDetails) {
         if (billingDetails == null)
             throw new IllegalArgumentException("Can't add a null BillingDetails.");
         this.getBillingDetails().add(billingDetails);

         if (getBillingDetails().size() == 1) {
             setDefaultBillingDetails(billingDetails);
         }
    }
    /**
     * Removes a <tt>BillingDetails</tt> from the set.
     * <p>
     * This method checks if the removed is the default element,
     * and will throw a BusinessException if there is more than one
     * left to chose from. This might actually not be the best way
     * to handle this situation.
     *
     * @param billingDetails
     * @throws BusinessException
     */
    public void removeBillingDetails(BillingDetails billingDetails)
        throws BusinessException {
        if (billingDetails == null)
            throw new IllegalArgumentException("Can't add a null BillingDetails.");

        if (getBillingDetails().size() >= 2) {
            getBillingDetails().remove(billingDetails);
            setDefaultBillingDetails((BillingDetails)getBillingDetails().iterator().next());
        } else {
            throw new BusinessException("Please set new default BillingDetails first");
        }
    }

    public BillingDetails getDefaultBillingDetails() { return defaultBillingDetails; }
    public void setDefaultBillingDetails(BillingDetails defaultBillingDetails) {
        this.defaultBillingDetails = defaultBillingDetails;
    }

    public Collection<Item> getItemsForSale() { return itemsForSale; }
    public void setItemsForSale(Collection<Item> itemsForSale) { this.itemsForSale = itemsForSale; }

    public Set<Item> getBoughtItems() { return boughtItems; }
    public void addBoughtItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Null Item!");
        item.setBuyer(this);
        boughtItems.add(item);
    }

    public Date getCreated() { return created; }

    // ********************** Common Methods ********************** //

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        final User user = (User) o;
        return getUsername().equals(user.getUsername());
    }

    public int hashCode() {
        return getUsername().hashCode();
    }

    public String toString() {
        return  "User ('" + getId() + "'), " +
                "Username: '" + getUsername() + "'";
    }

    public int compareTo(Object o) {
        if (o instanceof User)
            // Don't compare Date objects! Use the time in milliseconds!
            return Long.valueOf(this.getCreated().getTime()).compareTo(
                    Long.valueOf( ((User)o).getCreated().getTime())
                   );
        return 0;
    }

    // ********************** Business Methods ********************** //


}
