package com.baeldung.lombok.builder.singular;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
public class Sea {

    @Singular private final List<String> grasses;
    @Singular("oneFish") private final List<String> fish;
}
