package org.baeldung.examples.olingo4.domain;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="car_model")
public class CarModel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private String name;
    
    @NotNull
    private Integer year;
    
    @NotNull
    private String sku;
    
    @ManyToOne(optional=false, fetch= FetchType.EAGER   )
    @JoinColumn(name="maker_fk")
    private CarMaker maker;

}
