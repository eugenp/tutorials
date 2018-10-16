package com.hexagonal.demo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "area")
public class Area {
    @Id
    @Column(name ="zipcode")
    private String zipCode;
    private String city;
    private String province;

}
