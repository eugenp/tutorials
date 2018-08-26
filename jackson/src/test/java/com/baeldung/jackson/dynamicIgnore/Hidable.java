package com.baeldung.jackson.dynamicIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("hidden")
public interface Hidable {
    boolean isHidden();
}
