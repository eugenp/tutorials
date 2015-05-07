package org.baeldung.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Foo implements Serializable {

    private static final long serialVersionUID = 1L;

    public Foo() {
        super();
    }

    public Foo(final String name) {
        super();

        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;

    @ManyToOne(targetEntity = Bar.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "BAR_ID")
    private Bar bar;

    public Bar getBar() {
        return bar;
    }

    public void setBar(final Bar bar) {
        this.bar = bar;
    }

    public long getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

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
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Foo [name=").append(name).append("]");
        return builder.toString();
    }

}
