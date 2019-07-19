package com.baeldung.tutorials.hexagonal.arch.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "order_table")
@Data
public class Order {
 
    @Id
    @GeneratedValue
    @Column
    private int id;
 
    @Column
    private int unitCount;
}