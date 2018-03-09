package datajpa.domain.domain4;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "CATEGORIZED_ITEM")
public class CategorizedItem {
    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "CATEGORY_ID")
        private Long categoryId;

        @Column(name = "ITEM_ID")
        private Long itemId;

        public Id() {

        }

        public Id(Long categoryId, Long itemId) {
            this.categoryId = categoryId;
            this.itemId = itemId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof Id) {
                Id that = (Id) o;
                return this.categoryId.equals(that.categoryId) && this.itemId.equals(that.itemId);
            } else {
                return false;
            }
        }

        public int hashCode() {
            return categoryId.hashCode() + itemId.hashCode();
        }

    }

    @EmbeddedId
    private Id id = new Id();

    @Column(name = "ADDED_BY_USER")
    private String username;

    @Column(name = "ADDED_ON")
    private Date dateAdded = new Date();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEM_ID",
            insertable = false, updatable = false)
    private Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID",
            insertable = false, updatable = false)
    private Category category;

    public CategorizedItem() {

    }

    public CategorizedItem(String username, Category category, Item item) {
        // Set fields
        this.username = username;

        this.category = category;
        this.item = item;

        // Set identifier values
        this.id.categoryId = category.getId();
        this.id.itemId = item.getId();

        // Guarantee referential integrity
        category.getCategorizedItems().add(this);
//        item.getCategorizedItems().add(this);
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
