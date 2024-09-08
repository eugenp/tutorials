package com.baeldung.copy.deep;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import com.baeldung.copy.shallow.Founder;

public class Company implements Cloneable, Serializable {

    private String companyName;
    private Founder founder;

    public Company(String companyName, Founder founder) {
        this.companyName = companyName;
        this.founder = founder;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Founder getFounder() {
        return founder;
    }

    public void setFounder(Founder founder) {
        this.founder = founder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        return Objects.equals(companyName, company.companyName) && Objects.equals(founder, company.founder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, founder);
    }

    @Override
    public Company clone() {
        try {
            Company clone = (Company) super.clone();
            clone.founder = new Founder(this.founder.getName(), this.founder.getYear());
            return clone;
        } catch (CloneNotSupportedException e) {
            // shouldn't happen since we're Cloneable
            throw new RuntimeException(e);
        }
    }

    public Company createSerializedClone() throws IOException, ClassNotFoundException {

        byte[] serialization;
        try (ByteArrayOutputStream arrayIn = new ByteArrayOutputStream()) {
            try (ObjectOutputStream out = new ObjectOutputStream(arrayIn)) {
                out.writeObject(this);
                serialization = arrayIn.toByteArray();
            }
        }
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(serialization)) {
            try (ObjectInputStream in = new ObjectInputStream(byteIn)) {
                return (Company) in.readObject();
            }
        }
    }
}
