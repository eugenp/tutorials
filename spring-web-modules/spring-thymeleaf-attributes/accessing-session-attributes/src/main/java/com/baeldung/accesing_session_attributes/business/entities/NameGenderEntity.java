package com.baeldung.accesing_session_attributes.business.entities;

/**
 * NameGenderModel
 * https://api.genderize.io/?name=victor
 */
public class NameGenderEntity {
    private Long count;
    private String gender;
    private String name;
    private Float probability;

    public NameGenderEntity() {
        super();
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((count == null) ? 0 : count.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((probability == null) ? 0 : probability.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NameGenderEntity other = (NameGenderEntity) obj;
        if (count == null) {
            if (other.count != null)
                return false;
        } else if (!count.equals(other.count))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (probability == null) {
            if (other.probability != null)
                return false;
        } else if (!probability.equals(other.probability))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NameGenderModel [count=" + count + ", gender=" + gender + ", name=" + name + ", probability=" + probability + "]";
    }

}