package datajpa.domain.domain4;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lihongjie on 3/31/17.
 * OneToMany
 */
@Entity(name = "ITEM_4")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "item_description")
    private String description;

    @Column(name = "initial_price")
    private BigDecimal initialPrice;

    @Column(name = "reserve_price")
    private BigDecimal reservePrice;

    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "state")
    private String state;
    @Column(name = "approval_datetime")
    private Date approvalDatetime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    @OrderColumn(name = "BID_POSITION")
/*@OneToMany
@JoinColumn(name = "ITEM_ID", nullable = false, insertable = false, updatable = false)
@IndexColumn(name = "BID_POSITION")*/
    private List<Bid> bids = new ArrayList<Bid>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ITEM_BUYER",
            joinColumns = {@JoinColumn(name = "ITEM_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")}
    )
    private User buyer;
/*
    @ManyToMany(mappedBy = "items", cascade = CascadeType.ALL)
    private Set<Category> categories = new HashSet<Category>();*/

    @OneToMany(mappedBy = "item")
    private Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getApprovalDatetime() {
        return approvalDatetime;
    }

    public void setApprovalDatetime(Date approvalDatetime) {
        this.approvalDatetime = approvalDatetime;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
        bid.setItem(this);
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

/*    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }*/

    public Set<CategorizedItem> getCategorizedItems() {

        return categorizedItems;
    }

    public void setCategorizedItems(Set<CategorizedItem> categorizedItems) {

        this.categorizedItems = categorizedItems;
    }
}
