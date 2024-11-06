package com.baeldung.spring.data.jpa.optionalfields.projection;

public interface BookProjection {
    Integer getId();

    String getTitle();

    String getAuthor();
}
