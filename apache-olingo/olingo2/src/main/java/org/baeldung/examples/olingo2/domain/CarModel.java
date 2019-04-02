package org.baeldung.examples.olingo2.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class CarModel {

    @Id
    private Long id;
    
    @NotNull
    private String name;
    
    @NotNull
    private Integer year;
    
    @NotNull
    private String sku;
    
    @ManyToOne
    private CarMaker maker;

}
