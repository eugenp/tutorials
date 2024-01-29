package com.baeldung.listvsset.eager.set.fulldomain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
