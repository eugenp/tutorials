package com.baeldung.persistence.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;

import org.hibernate.envers.Audited;

@NamedNativeQueries({
  @NamedNativeQuery(name = "callGetAllFoos", query = "CALL GetAllFoos()", resultClass = Foo.class),
  @NamedNativeQuery(name = "callGetFoosByName", query = "CALL GetFoosByName(:fooName)", resultClass = Foo.class)
})
@NamedStoredProcedureQuery(
		  name="GetAllFoos",
		  procedureName="GetAllFoos",
		  resultClasses = { Foo.class }
		)
@NamedStoredProcedureQuery(
		  name="GetFoosByName",
		  procedureName="GetFoosByName",
		  resultClasses = { Foo.class },
		  parameters={
		    @StoredProcedureParameter(name="fooName", type=String.class, mode=ParameterMode.IN)
		  }
		)

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
