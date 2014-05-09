package org.baeldung.persistence.model;

import java.io.Serializable;
import java.util.Set;
import com.google.common.collect.Sets;
import javax.persistence.*;
import org.hibernate.annotations.OrderBy;

@Entity
@NamedQuery(name = "Bar.findAll", query = "SELECT b FROM Bar b")
public class Bar implements Serializable {

    public Bar() {
        super();
    }

    public Bar(final String name) {
        super();
    
        this.name = name;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @OneToMany(mappedBy = "bar", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy(clause = "NAME DESC")
    Set<Foo> fooSet = Sets.newHashSet();

    //API
    
    public Set<Foo> getFooSet() {
        return fooSet;
    }

    public void setFooList(Set<Foo> fooSet) {
        this.fooSet = fooSet;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Bar other = (Bar) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Bar [name=").append(name).append("]");
        return builder.toString();
    }


}
