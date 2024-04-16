package com.baeldung.builder.implementation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LombokPost {

    private String title;

    private String text;

    private String category;

}
