package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
public class ResumeWithSetterInjection {

    private IProgrammingLanguage favoriteLanguage;

    @Autowired
    public void setFavoriteLanguage(IProgrammingLanguage favoriteLanguage) {
        this.favoriteLanguage = favoriteLanguage;
    }
}