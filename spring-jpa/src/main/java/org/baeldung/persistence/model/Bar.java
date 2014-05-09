package org.baeldung.persistence.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;

@Entity
public class Bar implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @OneToMany(mappedBy = "bar", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    List<Foo> fooList;
    
    private String name;
    
    public Bar(){
        super();
    }
    
    public Bar(final String name){
        super();
        this.name = name;
    }

    //API
    
    public List<Foo> getFooList() {
        return fooList;
    }
    
    public void setFooList(List<Foo> fooList) {
        this.fooList = fooList;
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
