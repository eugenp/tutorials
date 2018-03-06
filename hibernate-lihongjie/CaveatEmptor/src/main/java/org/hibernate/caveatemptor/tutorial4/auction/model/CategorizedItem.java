package org.hibernate.caveatemptor.tutorial4.auction.model;

import java.io.Serializable;
import java.util.Date;

/**
 * A single item in a single category, with additional information.
 * <p>
 * This is really a very special mapping. The CategorizedItem class
 * represents a join table. The ER model for this is really
 * a many-to-many association, but instead of two entities and two
 * collections, we mapped this as two one-to-many associations between
 * three entities. One of the motivation for this are the additional
 * attributes on the association table (not only two FKs): username
 * and creation date.
 * <p>
 * To create a new link between a Category and an Item, instantiate
 * an object of this class using the right constructor. Note that
 * the Category and Item have to have an identifier value, they have
 * to be either in persistent or detached state. Transient Category
 * and Item instances don't work.
 *
 * @see auction.model.Category
 * @see auction.model.Item
 * @author Christian Bauer
 */
public class CategorizedItem implements Serializable, Comparable {

    /**
     * Emedded composite identifier class that represents the
     * primary key columns of the many-to-many join table.
     */
    public static class Id implements Serializable {
		private Long categoryId;
		private Long itemId;

        public Id() {}

        public Id(Long categoryId, Long itemId) {
			this.categoryId = categoryId;
			this.itemId = itemId;
		}

		public boolean equals(Object o) {
			if (o instanceof Id) {
				Id that = (Id)o;
				return this.categoryId.equals(that.categoryId) &&
					   this.itemId.equals(that.itemId);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return categoryId.hashCode() + itemId.hashCode();
		}
	}

	private Id id;
	private String username; // This could also be a many-to-one association to User
	private Date dateAdded = new Date();
	private Item item;
	private Category category;

	/**
	 * No-arg constructor for JavaBean tools
	 */
    public CategorizedItem() {}

	/**
	 * Full constructor, the Category and Item instances have to have an
     * identifier value, they have to be in detached or persistent state.
     * This constructor takes care of the bidirectional relationship by
     * adding the new instance to the collections on either side of the
     * many-to-many association (added to the collections).
	 */
	public CategorizedItem(String username, Category category, Item item) {
		this.username = username;

		this.category = category;
		this.item = item;

		// Set primary key
        this.id = new Id(category.getId(), item.getId());

		// Guarantee referential integrity
		category.getCategorizedItems().add(this);
		item.getCategorizedItems().add(this);
	}

	// ********************** Accessor Methods ********************** //

	public Id getId() { return id; }

	public String getUsername() { return username; }
	public Date getDateAdded() { return dateAdded; }

	public Category getCategory() { return category; }
	public Item getItem() { return item; }

	// ********************** Common Methods ********************** //

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategorizedItem that = (CategorizedItem) o;
        return id.equals(that.id);
    }

    public int hashCode() {
        return id.hashCode();
    }

    public int compareTo(Object o) {
		// CategorizedItems are sorted by date
		if (o instanceof CategorizedItem)
			return getDateAdded().compareTo( ((CategorizedItem)o).getDateAdded() );
		return 0;
	}

	public String toString() {
		return  "Added by: '" + getUsername() + "', " +
				"On Date: '" + getDateAdded();
	}

	// ********************** Business Methods ********************** //

}