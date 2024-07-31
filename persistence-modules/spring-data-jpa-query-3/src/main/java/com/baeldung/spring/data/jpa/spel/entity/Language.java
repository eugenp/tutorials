package com.baeldung.spring.data.jpa.spel.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Language {

    @Id
    @Column(name = "iso_code")
    private String isoCode;

    public Language() {
    }

    public Language(final String isoCode) {
        this.isoCode = isoCode;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(final String isoCode) {
        this.isoCode = isoCode;
    }

    @Override
    public String toString() {
        return "Language{" +
               "isoCode='" + isoCode + '\'' +
               '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Language language = (Language) o;

        return Objects.equals(isoCode, language.isoCode);
    }

    @Override
    public int hashCode() {
        return isoCode != null ? isoCode.hashCode() : 0;
    }
}

