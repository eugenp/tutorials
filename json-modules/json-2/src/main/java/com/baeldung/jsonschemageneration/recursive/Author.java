package com.baeldung.jsonschemageneration.recursive;

import java.util.List;
import java.util.UUID;

public class Author {
    private UUID id;
    private String name;
    private String role;

    private List<AuthoredArticle> articles;
}
