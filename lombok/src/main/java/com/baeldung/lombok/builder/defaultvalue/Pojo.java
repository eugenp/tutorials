package com.baeldung.lombok.builder.defaultvalue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Pojo {
    private final String name = "foo";
    private final boolean original = true;
}
