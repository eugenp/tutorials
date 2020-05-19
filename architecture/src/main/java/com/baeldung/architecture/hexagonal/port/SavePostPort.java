package com.baeldung.architecture.hexagonal.port;

import com.baeldung.architecture.hexagonal.Post;

public interface SavePostPort {

    void save(Post post);
}
