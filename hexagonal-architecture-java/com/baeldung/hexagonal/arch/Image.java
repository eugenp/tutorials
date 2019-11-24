package com.baeldung.hexagonal.arch;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

// Has standard setters and getters
@Data
@NoArgsConstructor
class Image {
    private String name;
    private Integer size;
    private List<String> tags;
}
