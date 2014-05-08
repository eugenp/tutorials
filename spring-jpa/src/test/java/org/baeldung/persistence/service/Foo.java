package org.baeldung.persistence.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Column;





/**
 * The persistent class for the FOO database table.
 * 
 */
@Entity
@NamedQuery(name="Foo.findAll", query="SELECT f FROM Foo f")
public class Foo implements Serializable {
	private static final long serialVersionUID = 1L;


	public Foo() {
		super();
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="NAME")
	private String name;

	@ManyToOne(targetEntity= Bar.class,fetch=FetchType.EAGER)
	@JoinColumn(name="BAR_ID")
	private Bar bar;

	public Bar getBar() {
		return bar;
	}

	public void setBar(final Bar bar) {
		this.bar = bar;
	}
	public int getId() {
		return this.id;
	}

    public void setId(final int id) {
		this.id = id;
	}

	
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
		
}