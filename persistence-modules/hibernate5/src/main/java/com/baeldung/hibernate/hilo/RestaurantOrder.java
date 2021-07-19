package com.baeldung.hibernate.hilo;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RestaurantOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_sequence_generator")
    @GenericGenerator(
      name = "hilo_sequence_generator",
      strategy = "sequence",
      parameters = {
        @Parameter(name = "sequence_name", value = "hilo_seqeunce"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "3"),
        @Parameter(name = "optimizer", value = "hilo")
      }
    )
    private Long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
