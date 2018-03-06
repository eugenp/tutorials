package org.hibernate.caveatemptor.tutorial3.auction.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A single item in a single category, with additional information.
 * <p>
 * This is really a very special mapping. The CategorizedItemComponent
 * class represents a join table. The ER model for this is really
 * a many-to-many association, but instead of two entities and two
 * collections, we mapped this as a single collection (in Category)
 * of composite elements, instances of this class. This simplifies
 * the lifecycle of the association. Navigation is however only
 * possible from Category -> CategorizedItemComponent -> Item, not
 * in the other direction.
 *
 * @see Category
 * @see Item
 * @author Christian Bauer
 */
@Embeddable
public class CategorizedItemComponent implements Serializable, Comparable {

    @Column(name = "ADDED_BY_USER", nullable = false, updatable = false, length = 16)
    private String username; // This could also be a many-to-one association to User

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_ON", nullable = false, updatable = false)
    private Date dateAdded = new Date();

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false, updatable = false)
    @org.hibernate.annotations.ForeignKey(name = "FK_CATEGORIZED_ITEM_COMPONENT_ITEM_ID")
    private Item item;

    @org.hibernate.annotations.Parent
    private Category category;

	/**
	 * No-arg constructor for JavaBean tools
	 */
    CategorizedItemComponent() {}

	/**
	 * Full constructor
	 */
	public CategorizedItemComponent(String username, Category category, Item item) {
		this.username = username;
		this.category = category;
		this.item = item;
	}

	// ********************** Accessor Methods ********************** //

    public String getUsername() { return username; }

    public Date getDateAdded() { return dateAdded; }

    public Item getItem() { return item; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; } // TODO: Hibernate requires this?

    // ********************** Common Methods ********************** //

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CategorizedItemComponent that = (CategorizedItemComponent) o;

        if (!category.equals(that.category)) return false;
        if (!dateAdded.equals(that.dateAdded)) return false;
        if (!item.equals(that.item)) return false;
        if (!username.equals(that.username)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = username.hashCode();
        result = 29 * result + dateAdded.hashCode();
        result = 29 * result + item.hashCode();
        result = 29 * result + category.hashCode();
        return result;
    }

    public int compareTo(Object o) {
		// CategorizedItems are sorted by date
		if (o instanceof CategorizedItemComponent)
			return getDateAdded().compareTo( ((CategorizedItemComponent)o).getDateAdded() );
		return 0;
	}

	public String toString() {
		return  "Added by: '" + getUsername() + "', " +
				"On Date: '" + getDateAdded();
	}

	// ********************** Business Methods ********************** //

}