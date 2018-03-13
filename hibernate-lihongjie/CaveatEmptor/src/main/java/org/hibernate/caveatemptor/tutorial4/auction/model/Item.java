package org.hibernate.caveatemptor.tutorial4.auction.model;

import auction.exceptions.*;

import java.io.Serializable;
import java.util.*;

/**
 * An item for auction.
 *
 * @author Christian Bauer
 */
public class Item implements Serializable, Comparable, Auditable {

    private Long id = null;
    private int version = 0;

    private String name;
    private User seller;
    private User buyer;
    private String description;

    private MonetaryAmount initialPrice;
    private MonetaryAmount reservePrice;
    private Date startDate;
    private Date endDate;

    private ItemState state;
    private User approvedBy;
    private Date approvalDatetime;

    private List<Bid> bids = new ArrayList<Bid>();
    private Bid successfulBid;
    private Map<Long,Bid> bidsByIdentifier = new HashMap<Long,Bid>(); // Not very useful

    private Set<Category> categories = new HashSet<Category>();
    private Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>();

    private Collection<String> images = new ArrayList<String>();

    private Date created = new Date();

    /**
     * No-arg constructor for JavaBean tools.
     */
    public Item() {}

    /**
     * Full constructor
     */
    public Item(String name, User seller, User buyer, String description,
                MonetaryAmount initialPrice, MonetaryAmount reservePrice, Date startDate, Date endDate,
                ItemState state, User approvedBy, Date approvalDatetime,
                List<Bid> bids, Bid successfulBid, Map<Long, Bid> bidsByIdentifier,
                Set<Category> categories, Set<CategorizedItem> categorizedItems,
                Collection<String> images) {
        this.name = name;
        this.seller = seller;
        this.buyer = buyer;
        this.description = description;
        this.initialPrice = initialPrice;
        this.reservePrice = reservePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.approvedBy = approvedBy;
        this.approvalDatetime = approvalDatetime;
        this.bids = bids;
        this.successfulBid = successfulBid;
        this.bidsByIdentifier = bidsByIdentifier;
        this.categories = categories;
        this.categorizedItems = categorizedItems;
        this.images = images;

        // Referential integrity
        seller.getItemsForSale().add(this);
    }

    /**
     * Simple constructors
     */
    public Item(String name, String description, User seller,
                MonetaryAmount initialPrice, MonetaryAmount reservePrice,
                Date startDate, Date endDate) {
        this.name = name;
        this.seller = seller;
        this.description = description;
        this.initialPrice = initialPrice;
        this.reservePrice = reservePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = ItemState.DRAFT;

        // Referential integrity
        seller.getItemsForSale().add(this);
    }

    // ********************** Accessor Methods ********************** //

    public Long getId() { return id; }
    public int getVersion() { return version; }

    public String getName() { return name; }

    public User getSeller() { return seller; }

    public User getBuyer() { return buyer; }
    public void setBuyer(User buyer) { this.buyer = buyer; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MonetaryAmount getInitialPrice() { return initialPrice; }

    public MonetaryAmount getReservePrice() { return reservePrice; }

    public Date getStartDate() { return startDate; }

    public Date getEndDate() { return endDate; }

    public ItemState getState() { return state; }

    public User getApprovedBy() { return approvedBy; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }

    public Date getApprovalDatetime() { return approvalDatetime; }
    public void setApprovalDatetime(Date approvalDatetime) { this.approvalDatetime = approvalDatetime; }

    public List<Bid> getBids() { return bids; }
    public void addBid(Bid bid) {
        if (bid == null)
            throw new IllegalArgumentException("Can't add a null Bid.");
        this.getBids().add(bid);
        // Don't have to set the "other" side, a Bid can only be instantiated with a given item
    }

    public Bid getSuccessfulBid() { return successfulBid; }
    public void setSuccessfulBid(Bid successfulBid) {
        // Has to preserve the integrity by using a procedure and loop, bad Java...
        if (successfulBid != null) {
            for (Bid bid : getBids())
                bid.setSuccessful(false);
            successfulBid.setSuccessful(true);
            this.successfulBid = successfulBid;
        }
    }

    public Map<Long, Bid> getBidsByIdentifier() { return bidsByIdentifier; }
    public void setBidsByIdentifier(Map<Long, Bid> bidsByIdentifier) { this.bidsByIdentifier = bidsByIdentifier; }

    // Read-only, modify through Category#addItem() and Category@removeItem();
    public Set<Category> getCategories() { return Collections.unmodifiableSet(categories); }

    // Read-only, to create a link, instantiate a CategorizedItem with the right constructor
    // To remove a link, use Category.getCategorizedItems().remove()
    public Set<CategorizedItem> getCategorizedItems() { return categorizedItems; }

    public Collection<String> getImages() { return images; }

    public Date getCreated() { return created; }

    // ********************** Common Methods ********************** //

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        final Item item = (Item) o;

        if (! (created.getTime() == item.created.getTime()) ) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 29 * result + created.hashCode();
        return result;
    }

    public String toString() {
        return  "Item ('" + getId() + "'), " +
                "Name: '" + getName() + "' " +
                "Initial Price: '" + getInitialPrice()+ "'";
    }

    public int compareTo(Object o) {
        if (o instanceof Item) {
            // Don't compare Date objects! Use the time in milliseconds!
            return Long.valueOf(this.getCreated().getTime()).compareTo(
                    Long.valueOf( ((Item)o).getCreated().getTime())
                   );
        }
        return 0;
    }

    // ********************** Business Methods ********************** //
    
    /**
     * Places a bid while checking business constraints.
     *
     * This method may throw a BusinessException if one of the requirements
     * for the bid placement wasn't met, e.g. if the auction already ended.
     *
     * @param bidder
     * @param bidAmount
     * @param currentMaxBid  the most valuable bid for this item
     * @param currentMinBid  the least valuable bid for this item
     * @return
     * @throws BusinessException
     */
    public Bid placeBid(User bidder, MonetaryAmount bidAmount,
                        Bid currentMaxBid, Bid currentMinBid)
        throws BusinessException {

        // Check initial price
        if (initialPrice.compareTo(bidAmount) > 0) {
            throw new BusinessException("Bid lower than initial price");
        }

        // Check reserve price
        if (reservePrice.compareTo(bidAmount) > 0) {
            throw new BusinessException("Bid lower than reserve price");
        }

        // Check highest bid (can also be a different Strategy (pattern))
        if (currentMaxBid != null && currentMaxBid.getAmount().compareTo(bidAmount) > 0) {
            throw new BusinessException("Bid too low");
        }

        // Auction is active
        if ( !this.getState().equals(ItemState.ACTIVE) )
            throw new BusinessException("Auction is not active yet");

        // Auction still valid
        if ( this.getEndDate().before( new Date() ) )
            throw new BusinessException("Can't place new bid, auction already ended");

        // Create new Bid
        Bid newBid = new Bid(bidAmount, this, bidder);

        // Place bid for this Item
        this.addBid(newBid);

        return newBid;
    }

    /**
     * Anyone can set this item into PENDING state for approval.
     */
    public void setPendingForApproval() {
        state = ItemState.PENDING;
    }

    /**
     * Approve this item for auction and set its state to active.
     *
     * @param byUser
     * @throws BusinessException
     */
    public void approve(User byUser) throws BusinessException {

        if ( !byUser.isAdmin() )
            throw new PermissionException("Not an administrator");

        if ( !state.equals(ItemState.PENDING) )
            throw new IllegalStateException("Item still in draft");

        state = ItemState.ACTIVE;
        approvedBy = byUser;
        approvalDatetime = new Date();
    }

}
