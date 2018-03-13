package com.habuma.spitter.domain;

import static javax.persistence.GenerationType.*;
import static org.apache.commons.lang.builder.EqualsBuilder.*;
import static org.apache.commons.lang.builder.HashCodeBuilder.*;
import static org.apache.commons.lang.builder.ToStringBuilder.*;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="spittle")
public class Spittle implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long id;
  private Spitter spitter;
  private String text;
  
  @DateTimeFormat(pattern="hh:mma MMM d, YYYY")
  private Date when;

  public Spittle() {
    this.spitter = new Spitter();  // HARD-CODED FOR NOW
    this.spitter.setId((long)1);
  }
  
  @Id
  @GeneratedValue(strategy = AUTO)
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  @Column(name="spittleText")
  @NotNull
  @Size(min=1, max=140)
  public String getText() {
    return this.text;
  }
  
  public void setText(String text) {
    this.text = text;
  }
  
  @Column(name="postedTime")
  public Date getWhen() {
    return this.when;
  }

  public void setWhen(Date when) {
    this.when = when;
  }

  @ManyToOne
  @JoinColumn(name="spitter_id")
  public Spitter getSpitter() {
    return this.spitter;
  }

  public void setSpitter(Spitter spitter) {
    this.spitter = spitter;
  }
  
  // plumbing
  @Override
  public boolean equals(Object obj) {
    return reflectionEquals(this, obj);
  }
  
  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }
  
  @Override
  public String toString() {
    return reflectionToString(this);
  }
}
