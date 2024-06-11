package com.baeldung.builder.implementation;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LombokPost {

    private String title;

    private String text;

    private String category;

}
