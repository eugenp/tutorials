package org.hibernate.caveatemptor.tutorial4.auction.model;

import org.hibernate.caveatemptor.tutorial4.auction.exceptions.BusinessException;

import java.io.Serializable;
import java.util.*;

/**
 * A user of the CaveatEmptor auction application.
 *
 * @author Christian Bauer
 */
public class User implements Serializable, Comparable {

    private Long id = null;
    private int version = 0;

    private String firstname;
    private String lastname;
    private String username; // Unique and immutable
    private String password;
    private String email;
    private int ranking = 0;

    private boolean admin = false;

    private Address homeAddress;
    private Address billingAddress;
    private AddressEntity shippingAddress;

    private Set<BillingDetails> billingDetails = new HashSet<BillingDetails>();
    private BillingDetails defaultBillingDetails;

    private Collection<Item> itemsForSale = new ArrayList<Item>();
    private Set<Item> boughtItems = new HashSet<Item>();

    private Date created = new Date();

    /**
     * No-arg constructor for JavaBean tools
     */
    public User() {
    }

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

    public Long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public AddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Set getBillingDetails() {
        return billingDetails;
    }

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
    public void removeBillingDetails(BillingDetails billingDetails) throws BusinessException {
        if (billingDetails == null)
            throw new IllegalArgumentException("Can't add a null BillingDetails.");

        if (getBillingDetails().size() >= 2) {
            getBillingDetails().remove(billingDetails);
            setDefaultBillingDetails((BillingDetails) getBillingDetails().iterator().next());
        } else {
            throw new BusinessException("Please set new default BillingDetails first");
        }
    }

    public BillingDetails getDefaultBillingDetails() {
        return defaultBillingDetails;
    }

    public void setDefaultBillingDetails(BillingDetails defaultBillingDetails) {
        this.defaultBillingDetails = defaultBillingDetails;
    }

    public Collection<Item> getItemsForSale() {
        return itemsForSale;
    }

    public void setItemsForSale(Collection<Item> itemsForSale) {
        this.itemsForSale = itemsForSale;
    }

    public Set<Item> getBoughtItems() {
        return boughtItems;
    }

    public void addBoughtItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Null Item!");
        item.setBuyer(this);
        boughtItems.add(item);
    }

    public Date getCreated() {
        return created;
    }

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
        return "User ('" + getId() + "'), " +
                "Username: '" + getUsername() + "'";
    }

    public int compareTo(Object o) {
        if (o instanceof User)
            // Don't compare Date objects! Use the time in milliseconds!
            return Long.valueOf(this.getCreated().getTime()).compareTo(
                    Long.valueOf(((User) o).getCreated().getTime())
            );
        return 0;
    }

    // ********************** Business Methods ********************** //


}
