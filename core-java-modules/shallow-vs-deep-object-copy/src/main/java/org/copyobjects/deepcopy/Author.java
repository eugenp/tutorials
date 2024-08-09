package org.copyobjects.deepcopy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Author implements Cloneable{
    private String authorName;
    private String email;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}