package com.baeldung.architecture.hexagonal.port;

import java.util.UUID;

public interface CreatePostUseCase {

     UUID create(CreatePostRequest request);

}
