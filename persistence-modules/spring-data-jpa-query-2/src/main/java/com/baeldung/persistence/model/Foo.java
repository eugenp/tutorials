package com.baeldung.persistence.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import org.hibernate.envers.Audited;

@NamedNativeQueries({
  @NamedNativeQuery(name = "callGetAllFoos", query = "CALL GetAllFoos()", resultClass = Foo.class),
  @NamedNativeQuery(name = "callGetFoosByName", query = "CALL GetFoosByName(:fooName)", resultClass = Foo.class)
})
@Entity
@Audited
// @Proxy(lazy = false)
public class Foo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = Bar.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "BAR_ID")
    private Bar bar = new Bar();

    public Foo() {
        super();
    }

    public Foo(final String name) {
        super();
        this.name = name;
    }

    //

    public Bar getBar() {
        return bar;
    }

    public void setBar(final Bar bar) {
        this.bar = bar;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
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
        final Foo other = (Foo) obj;
        if (name == null) {
            return other.name == null;
        } else
            return name.equals(other.name);
    }

    @Override
    public String toString() {
        return "Foo [name=" + name + "]";
    }
}
