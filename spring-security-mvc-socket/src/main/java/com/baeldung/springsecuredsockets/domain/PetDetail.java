package com.baeldung.springsecuredsockets.domain;

import javax.persistence.*;

@Entity
@Table(name = "pet_detail")
public class PetDetail {

    @Id
    //Slight increase in performance over GenerationType.IDENTITY
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pet_detail_id", updatable = false, nullable = false)
    private long pet_detail_id;

    @Column(name = "description", nullable = false)
    private String description;

    /**
     * One to One Example - see Pet.
     * <p>
     * Each Pet has exactly one PetDetail.
     * Each PetDetail belongs to one Pet.
     */
    @OneToOne(mappedBy = "petDetail", cascade = CascadeType.ALL)
    private Pet pet;

    public long getPet_detail_id() {
        return pet_detail_id;
    }

    public void setPet_detail_idd(long pet_detail_id) {
        this.pet_detail_id = pet_detail_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}

