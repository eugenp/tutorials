package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import lombok.Data;

@Data
public class ResumeWithConstructorInjection {

    private IProgrammingLanguage favoriteLanguage;

    @Autowired
    public ResumeWithConstructorInjection(@Qualifier("favoriteLanguage") IProgrammingLanguage favoriteLanguage) {
        super();
        this.favoriteLanguage = favoriteLanguage;
    }
}
