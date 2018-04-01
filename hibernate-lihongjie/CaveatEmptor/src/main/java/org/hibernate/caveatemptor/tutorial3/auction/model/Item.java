package org.hibernate.caveatemptor.tutorial3.auction.model;

import auction.exceptions.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * An item for auction.
 *
 * @author Christian Bauer
 */
@NamedQueries({
    @NamedQuery(
        name = "Item-getMinBid",
        query = "select b from Bid b where b.amount.value = (select min(b.amount.value) from Bid b where b.item.id = :itemid)"
    ),
    @NamedQuery(
        name = "Item-getMaxBid",
        query = "select b from Bid b where b.amount.value = (select max(b.amount.value) from Bid b where b.item.id = :itemid)"
    ),
    @NamedQuery(
     name = "findItemsByDescription",
     query = "select i from Item i where i.description like :desc)",
     hints = {
      @QueryHint(name = "org.hibernate.comment", value = "My Comment"),
      @QueryHint(name = "org.hibernate.fetchSize", value = "50"),
      @QueryHint(name = "org.hibernate.flushMode", value = "never"),
      @QueryHint(name = "org.hibernate.readOnly", value = "true"),
      @QueryHint(name = "org.hibernate.timeout", value = "60")
     })
})
/*
@NamedNativeQueries({
    @NamedNativeQuery(
     name = "findItemsByDescriptionWithSQL",
     query = "select i.NAME, i.PRICE ... from ITEM i where i.DESC = :desc",
     resultClass = auction.model.Item.class
    )
})
*/

@Entity
@Table(name = "ITEM")
@org.hibernate.annotations.Filter(
    name = "limitItemsByUserRank",
    condition=":currentUserRank >= " +
              "(select u.RANK from USER u" +
              " where u.USER_ID = SELLER_ID)"
)
@org.hibernate.annotations.Check(
    constraints = "START_DATE < END_DATE"
)
@org.hibernate.annotations.Table(
    appliesTo = "ITEM",
    indexes =
      @org.hibernate.annotations.Index(
        name = "IDX_INITIAL_PRICE",
        columnNames = { "INITIAL_PRICE", "INITIAL_PRICE_CURRENCY" }
      )
)
public class Item implements Serializable, Comparable, Auditable {

    private static final String COLLECTION_ID_GENERATOR = "identity";

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id = null;

    @Version
    @Column(name = "OBJ_VERSION")
    private int version = 0;

    @Column(name = "ITEM_NAME", length = 255, nullable = false, updatable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SELLER_ID", nullable = false, updatable = false)
    @org.hibernate.annotations.ForeignKey(name = "FK_SELLER_ID")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
        name = "ITEM_BUYER",
        joinColumns = @JoinColumn(name = "ITEM_ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    // TODO: Foreign key name for ITEM_ID is ignored
    @org.hibernate.annotations.ForeignKey(
        name = "FK_ITEM_BUYER_USER_ID",
        inverseName = "FK_ITEM_BUYER_ITEM_ID"
    )
    private User buyer;

    @Column(name = "DESCRIPTION", length = 4000, nullable = false)
    private String description;

    @org.hibernate.annotations.Type(type = "monetary_amount_usd")
    @org.hibernate.annotations.Columns(columns = {
        @Column( name="INITIAL_PRICE", nullable = false, length = 2),
        @Column( name="INITIAL_PRICE_CURRENCY", nullable = false, length = 3)
    })
    private MonetaryAmount initialPrice;

    @org.hibernate.annotations.Type(type = "monetary_amount_usd")
    @org.hibernate.annotations.Columns(columns = {
        @Column( name="RESERVE_PRICE", nullable = false, length = 2),
        @Column( name="RESERVE_PRICE_CURRENCY", nullable = false, length = 3)
    })
    private MonetaryAmount reservePrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE", nullable = false, updatable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = false, updatable = false)
    @org.hibernate.annotations.Index(name = "IDX_END_DATE")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ITEM_STATE", nullable = false)
    private ItemState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVED_BY_USER_ID", nullable = true)
    @org.hibernate.annotations.ForeignKey(name = "FK_APPROVED_BY_USER_ID")
    private User approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPROVAL_DATETIME", nullable = true)
    private Date approvalDatetime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", nullable = false)
    @org.hibernate.annotations.IndexColumn(name = "BID_POSITION")
    @org.hibernate.annotations.BatchSize(size = 10)
    private List<Bid> bids = new ArrayList<Bid>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUCCESSFUL_BID_ID", nullable = true)
    @org.hibernate.annotations.ForeignKey(name = "FK_SUCCESSFUL_BID_ID")
    private Bid successfulBid;

    @MapKey(name="id")
    @OneToMany(mappedBy = "item")
    private Map<Long,Bid> bidsByIdentifier = new HashMap<Long,Bid>(); // Not very useful

    @ManyToMany(mappedBy = "items")
    private Set<Category> categories = new HashSet<Category>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>();

    @org.hibernate.annotations.CollectionOfElements
    @org.hibernate.annotations.CollectionId(
        columns = @Column(name = "ITEM_IMAGE_ID"),
        type = @org.hibernate.annotations.Type(type = "long"),
        generator = COLLECTION_ID_GENERATOR
    )
    @JoinTable(
        name = "ITEM_IMAGES",
        joinColumns = @JoinColumn(name = "ITEM_ID")
    )
    @Column(name = "FILENAME")
    @org.hibernate.annotations.ForeignKey(name = "FK_ITEM_IMAGE_ITEM_ID")
    private Collection<String> images = new ArrayList<String>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false, updatable = false)
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
