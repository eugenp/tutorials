package com.baeldung.hexagonal.architecture.infrastructure.driven.dataside.boundary;

import com.baeldung.hexagonal.architecture.infrastructure.driven.dataside.entity.BookEntity;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {

}
