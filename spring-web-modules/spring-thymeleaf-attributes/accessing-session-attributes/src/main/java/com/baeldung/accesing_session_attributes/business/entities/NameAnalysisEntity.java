package com.baeldung.accesing_session_attributes.business.entities;

public class NameAnalysisEntity {
    private String name;
    private NameAgeEntity age;
    private NameCountriesEntity countries;
    private NameGenderEntity gender;

    public NameAnalysisEntity() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NameAgeEntity getAge() {
        return age;
    }

    public void setAge(NameAgeEntity age) {
        this.age = age;
    }

    public NameCountriesEntity getCountries() {
        return countries;
    }

    public void setCountries(NameCountriesEntity countries) {
        this.countries = countries;
    }

    public NameGenderEntity getGender() {
        return gender;
    }

    public void setGender(NameGenderEntity gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((age == null) ? 0 : age.hashCode());
        result = prime * result + ((countries == null) ? 0 : countries.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
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
        NameAnalysisEntity other = (NameAnalysisEntity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (age == null) {
            if (other.age != null)
                return false;
        } else if (!age.equals(other.age))
            return false;
        if (countries == null) {
            if (other.countries != null)
                return false;
        } else if (!countries.equals(other.countries))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NameAnalysisEntity [name=" + name + ", age=" + age + ", countries=" + countries + ", gender=" + gender + "]";
    }
}
