package com.baeldung.springsecuredsockets.domain;

import javax.persistence.*;

@Entity
@Table(name = "PET")
public class Pet {

    public enum PetType {DRAGON, DOG, CAT, BULL, JARBUL}

    @Id
    //Slight increase in performance over GenerationType.IDENTITY
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "pet_id", updatable = false, nullable = false)
    private long pet_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType type;

    //Non-persisted
    @Transient
    private boolean exampleTransient;

    /**
     * One to One Example - see PetDetail.
     * <p>
     * Each Pet has exactly one PetDetail.
     * Each PetDetail belongs to one Pet.
     */
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pet_detail_id")
    private PetDetail petDetail;

    public long getPet_id() {
        return pet_id;
    }

    public void setPet_id(long pet_id) {
        this.pet_id = pet_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public boolean isExampleTransient() {
        return exampleTransient;
    }

    public void setExampleTransient(boolean exampleTransient) {
        this.exampleTransient = exampleTransient;
    }

    public PetDetail getPetDetail() {
        return petDetail;
    }

    public void setPetDetail(PetDetail petDetail) {
        this.petDetail = petDetail;
    }

    public String getTypeString() {
        return getType().toString();
    }

}
