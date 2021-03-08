package com.baeldung.autoproxying;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class User {
    @RandomInt(min = 2, max = 10)
    private int group;
}
