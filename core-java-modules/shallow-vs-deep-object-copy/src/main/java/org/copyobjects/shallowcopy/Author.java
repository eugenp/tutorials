package org.copyobjects.shallowcopy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Author {
    private String authorName;
    private String email;
}
