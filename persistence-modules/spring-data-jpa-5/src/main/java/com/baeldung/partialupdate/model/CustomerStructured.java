package com.baeldung.partialupdate.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CustomerStructured {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;
    @OneToMany(fetch = FetchType.EAGER, targetEntity = ContactPhone.class, mappedBy = "customerId") 
    public List<ContactPhone> contactPhones;

    @Override public String toString() {
        return String.format("Customer %s, Phone: %s", 
          this.name, this.contactPhones.stream()
          .map(e -> e.toString()).reduce("", String::concat));
    }
}
