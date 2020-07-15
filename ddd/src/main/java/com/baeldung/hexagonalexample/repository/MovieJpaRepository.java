package com.baeldung.hexagonalexample.repository;

import com.baeldung.hexagonalexample.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieJpaRepository extends JpaRepository<MovieEntity, Long> {
    @Query(
            value = "SELECT * FROM movie WHERE genre = ':genre' order by rating DESC limit 10",
            nativeQuery = true
    )
    public List<MovieEntity> findByGenre(@Param("genre") String genre);
}
