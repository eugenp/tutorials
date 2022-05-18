package com.baeldung.externalizable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Region extends Country implements Externalizable {

    private static final long serialVersionUID = 1L;

    private String climate;
    private Double population;
    private Community community;

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(Double population) {
        this.population = population;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeUTF(climate);
        community = new Community();
        community.setId(5);
        out.writeObject(community);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.climate = in.readUTF();
        community = (Community) in.readObject();
    }

    @Override
    public String toString() {
        return "Region = {" +
                "country='" + super.toString() + '\'' +
                "community='" + community.toString() + '\'' +
                "climate='" + climate + '\'' +
                ", population=" + population +
                '}';
    }
}
