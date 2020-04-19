package com.baeldung.jpa.multipletables.multipleentities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.baeldung.jpa.multipletables.secondarytable.MealAsSingleEntity;

@Entity
@Table(name = "allergens")
public class AllergensAsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long mealId;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "meal_id")
    private MealAsSingleEntity meal;

    @Column(name = "peanuts")
    private boolean peanuts;

    @Column(name = "celery")
    private boolean celery;

    @Column(name = "sesame_seeds")
    private boolean sesameSeeds;

    public MealAsSingleEntity getMeal() {
        return meal;
    }

    public void setMeal(MealAsSingleEntity meal) {
        this.meal = meal;
    }

    public boolean isPeanuts() {
        return peanuts;
    }

    public void setPeanuts(boolean peanuts) {
        this.peanuts = peanuts;
    }

    public boolean isCelery() {
        return celery;
    }

    public void setCelery(boolean celery) {
        this.celery = celery;
    }

    public boolean isSesameSeeds() {
        return sesameSeeds;
    }

    public void setSesameSeeds(boolean sesameSeeds) {
        this.sesameSeeds = sesameSeeds;
    }

    @Override
    public String toString() {
        return "AllergensAsEntity [peanuts=" + peanuts + ", celery=" + celery + ", sesameSeeds=" + sesameSeeds + "]";
    }

}
