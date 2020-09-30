package com.baeldung.domain.entities;

import javax.persistence.*;

@Entity
public class CarColor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_color_seq")
    @SequenceGenerator(
            name="car_color_seq",
            sequenceName = "car_color_seq",
            allocationSize = 1,
            initialValue = 1
    )
    private Long id;
    private String name;
    private Short red;
    private Short green;
    private Short blue;
    private Short alpha;    // a value between 0 and 100

    public CarColor() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getRed() {
        return red;
    }

    public void setRed(Short red) {
        this.red = red;
    }

    public Short getGreen() {
        return green;
    }

    public void setGreen(Short green) {
        this.green = green;
    }

    public Short getBlue() {
        return blue;
    }

    public void setBlue(Short blue) {
        this.blue = blue;
    }

    public Short getAlpha() {
        return alpha;
    }

    public void setAlpha(Short alpha) {
        this.alpha = alpha;
    }
}
