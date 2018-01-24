package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.Data;

@Data
public class ResumeWithFieldInjection {

    @Autowired
    private IProgrammingLanguage favoriteLanguage;

}
