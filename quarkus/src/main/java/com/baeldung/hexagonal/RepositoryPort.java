package com.baeldung.hexagonal;

import java.util.Objects;

public interface RepositoryPort {

    public void create( Object request);

    public Object view(int userId);
}
