package com.baeldung.component.autoproxying;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DataCache {
    @RandomInt(min = 2, max = 10)
    private int group;
    private String name;
}
