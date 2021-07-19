package com.baeldung.retrofit.models;

import com.google.gson.annotations.SerializedName;

public class Contributor {

    @SerializedName("login")
    private String name;

    private Integer contributions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getContributions() {
        return contributions;
    }

    public void setContributions(Integer contributions) {
        this.contributions = contributions;
    }

    @Override
    public String toString() {
        return "Contributer [name=" + name + ", contributions=" + contributions + "]";
    }

}
