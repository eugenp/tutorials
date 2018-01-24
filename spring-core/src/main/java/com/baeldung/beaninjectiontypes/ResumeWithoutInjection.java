package com.baeldung.beaninjectiontypes;

import lombok.Data;

@Data
public class ResumeWithoutInjection {

    private IProgrammingLanguage favoriteLanguage = new Java();

}
