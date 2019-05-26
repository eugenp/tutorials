package com.bealdung.contactbook.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contact {
    private String name;
    private String email;
}
