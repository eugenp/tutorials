package org.baeldung.examples.olingo2.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="car_maker")
public class CarMaker {
    
    @Id
    private Long id;
    
    @NotNull
    @Column(name="name")
    private String name;
    
    @OneToMany(mappedBy="maker",cascade=CascadeType.ALL)
    private List<CarModel> models;
    

}
