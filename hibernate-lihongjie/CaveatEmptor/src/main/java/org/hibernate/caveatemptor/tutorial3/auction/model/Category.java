package org.hibernate.caveatemptor.tutorial3.auction.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * The CaveatEmptor Category can have child categories and each has items.
 * <p>
 * Categories can be nested, this is expressed as a bidirectional one-to-many
 * relationship that references parent and child categories.
 * <p>
 * Each Category can have many items (and an item can be in many categories). This
 * is a many-to-many relationship. There are four strategies how you can map it.
 * <p>
 * First, the collection <tt>items</tt> is a true many-to-many association, with
 * collections on both sides. There are no additional columns in the underlying
 * many-to-many join table.
 * <p>
 * Second, the collection <tt>categorizedItems</tt> is a one-to-many association
 * to an entity class <tt>CategorizedItem</tt> that represents the link. The
 * <tt>Item</tt> class has the same collection mapped, to make it bidirectional.
 * This intermediate class represents additional columns on the many-to-many
 * join table, such as the user who added the item to the category, and the date
 * of the addition.
 * <p>
 * Third, the collection <tt>categorizedItemComponents</tt> is a collection of
 * value typed elements, of value type <tt>CategorizedItemComponent</tt>. This
 * simplifies management of the link (no intermediate entity class) but allows
 * only unidirectional navigation. The <tt>Item</tt> class does not know anything
 * about this collection or the components - no shared references.
 * <p>
 * Finally, the map <tt>itemsAndUser</tt> represents the many-to-many association
 * with a ternary relationship using a hash map. This map has item objects as keys,
 * and user objects as values. The underlying many-to-many join table has three
 * columns, <tt>CATEGORY_ID</tt>, <tt>ITEM_ID</tt>, and <tt>ADDED_BY_USER_ID</tt>.
 * This strategy allows you to map an additional column (the user foreign key) of
 * a many-to-many join table without writing an intermediate entity or component
 * class.
 *
 * @author Christian Bauer
 * @see Item
 * @see CategorizedItem
 * @see CategorizedItemComponent
 */
@Entity
@Table(
        name = "CATEGORY",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"CATEGORY_NAME", "PARENT_CATEGORY_ID"})
        // If you want a constraint name in the schema, use <database-object> in XML instead
)
public class Category implements Serializable, Comparable {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id = null;

    @Version
    @Column(name = "OBJ_VERSION")
    private int version = 0;

    @Column(name = "CATEGORY_NAME", length = 255, nullable = false)
    private String name;

    @OneToMany(mappedBy = "parentCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @org.hibernate.annotations.OrderBy(clause = "CATEGORY_NAME asc")
    private List<Category> childCategories = new ArrayList<Category>(); // A bag with SQL ORDER BY

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CATEGORY_ID", nullable = true)
    @org.hibernate.annotations.ForeignKey(name = "FK_CATEGORY_PARENT_ID")
    private Category parentCategory;

    @ManyToMany
    @JoinTable(
            name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    @org.hibernate.annotations.IndexColumn(name = "DISPLAY_POSITION")
    @org.hibernate.annotations.ForeignKey(
            name = "FK_CATEGORY_ITEM_CATEGORY_ID",
            inverseName = "FK_CATEGORY_ITEM_ITEM_ID"
    )
    @org.hibernate.annotations.Filter(
            name = "limitItemsByUserRank",
            condition = ":currentUserRank >= (select u.RANK from USERS u where u.USER_ID = SELLER_ID)"
    )
    private List<Item> items = new ArrayList<Item>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>();

    @org.hibernate.annotations.CollectionOfElements
    @JoinTable(
            name = "CATEGORIZED_ITEM_COMPONENTS",
            joinColumns = @JoinColumn(name = "CATEGORY_ID")
    )
    @org.hibernate.annotations.ForeignKey(name = "FK_CATEGORIZED_ITEM_COMPONENT_CATEGORY_ID")
    private Set<CategorizedItemComponent> categorizedItemComponents = new HashSet<CategorizedItemComponent>();

    @ManyToMany
    @org.hibernate.annotations.MapKeyManyToMany(
            joinColumns = @JoinColumn(name = "ITEM_ID")
    )
    @JoinTable(
            name = "CATEGORY_ITEMS_BY_USER",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @org.hibernate.annotations.ForeignKey(
            name = "FK_CATEGORY_ITEMS_BY_USER_CATEGORY_ID",
            inverseName = "FK_CATEGORY_ITEMS_BY_USER_USER_ID"
            // TODO: Can't declare the foreign key constraint name for ITEM_ID...
    )
    private Map<Item, User> itemsAndUser = new HashMap<Item, User>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false, updatable = false)
    private Date created = new Date();

    /**
     * No-arg constructor for JavaBean tools
     */
    public Category() {
    }

    /**
     * Full constructor
     */
    public Category(String name,
                    List<Category> childCategories,
                    Category parentCategory,
                    List<Item> items,
                    Set<CategorizedItem> categorizedItems,
                    Set<CategorizedItemComponent> categorizedItemComponents,
                    Map<Item, User> itemsAndUser) {
        this.name = name;
        this.childCategories = childCategories;
        this.parentCategory = parentCategory;
        this.items = items;
        this.categorizedItems = categorizedItems;
        this.categorizedItemComponents = categorizedItemComponents;
        this.itemsAndUser = itemsAndUser;
    }

    /**
     * Simple constructors
     */
    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    // ********************** Accessor Methods ********************** //

    public Long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getChildCategories() {
        return childCategories;
    }

    public void addChildCategory(Category childCategory) {
        if (childCategory == null) throw new IllegalArgumentException("Null child category!");
        if (childCategory.getParentCategory() != null)
            childCategory.getParentCategory().getChildCategories().remove(childCategory);
        childCategory.setParentCategory(parentCategory);
        childCategories.add(childCategory);
    }

    public void removeChildCategory(Category childCategory) {
        if (childCategory == null) throw new IllegalArgumentException("Null child category!");
        childCategory.setParentCategory(null);
        childCategories.remove(childCategory);
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    private void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    // Regular many-to-many
    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Null item!");
        items.add(item);
        item.getCategories().add(this);
    }

    public void removeItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Null item!");
        items.remove(item);
        item.getCategories().remove(this);
    }

    // Many-to-many with additional columns on join table, intermediate entity class
    // To create a link, instantiate a CategorizedItem with the right constructor
    // To remove a link, use getCategorizedItems().remove()
    public Set<CategorizedItem> getCategorizedItems() {
        return categorizedItems;
    }

    // Many-to-many with additional columns on join table, intermediate component class
    public Set<CategorizedItemComponent> getCategorizedItemComponents() {
        return categorizedItemComponents;
    }

    // Many-to-many with additional columns on join table, ternary hash map representation
    public Map<Item, User> getItemsAndUser() {
        return itemsAndUser;
    }

    public Date getCreated() {
        return created;
    }

    // ********************** Common Methods ********************** //

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Category category = (Category) o;

        if (!created.equals(category.created)) return false;
        if (!name.equals(category.name)) return false;
        return !(parentCategory != null ?
                !parentCategory.equals(category.parentCategory) :
                category.parentCategory != null);

    }

    public int hashCode() {
        int result;
        result = name.hashCode();
        result = 29 * result + (parentCategory != null ? parentCategory.hashCode() : 0);
        result = 29 * result + created.hashCode();
        return result;
    }

    public int compareTo(Object o) {
        if (o instanceof Category) {
            return this.getName().compareTo(((Category) o).getName());
        }
        return 0;
    }

    public String toString() {
        return "(" + getId() + ") Name: '" + getName();
    }

    // ********************** Business Methods ********************** //

}
