package com.baeldung.lombok;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuilderPerson {
    private Long id;
    private String username;
    private String password;
}