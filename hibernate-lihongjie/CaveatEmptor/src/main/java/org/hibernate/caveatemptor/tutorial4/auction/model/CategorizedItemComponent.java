package org.hibernate.caveatemptor.tutorial4.auction.model;

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
public class CategorizedItemComponent implements Serializable, Comparable {

    private String username; // This could also be a many-to-one association to User
    private Date dateAdded = new Date();
    private Item item;
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