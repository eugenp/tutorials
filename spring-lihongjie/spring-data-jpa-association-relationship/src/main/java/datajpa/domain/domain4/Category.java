package datajpa.domain.domain4;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Category_4")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;
    @Column(name = "NAME")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CATEGORY_ITEM_4",
            joinColumns = {@JoinColumn(name = "CATEGORY_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ITEM_ID")})
    private Set<Item> items = new HashSet<Item>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)

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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<CategorizedItem> getCategorizedItems() {
        return categorizedItems;
    }

    public void setCategorizedItems(Set<CategorizedItem> categorizedItems) {

        this.categorizedItems = categorizedItems;
    }
}
