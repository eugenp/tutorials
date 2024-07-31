package com.baeldung.hibernate.joincolumn;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

@Entity
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="ADDR_ID", referencedColumnName="ID"),
            @JoinColumn(name="ADDR_ZIP", referencedColumnName="ZIP")
    })
    private OfficeAddress address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfficeAddress getAddress() {
        return address;
    }

    public void setAddress(OfficeAddress address) {
        this.address = address;
    }
}