package com.baeldung.pojomapping;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Dog.class, name = "dog"),
    @JsonSubTypes.Type(value = Cat.class, name = "cat")
})
abstract class Animal {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Dog extends Animal {
    private int barkVolume;

    public int getBarkVolume() {
        return barkVolume;
    }

    public void setBarkVolume(int barkVolume) {
        this.barkVolume = barkVolume;
    }
}

class Cat extends Animal {
    private boolean likesFish;

    public boolean isLikesFish() {
        return likesFish;
    }

    public void setLikesFish(boolean likesFish) {
        this.likesFish = likesFish;
    }
}