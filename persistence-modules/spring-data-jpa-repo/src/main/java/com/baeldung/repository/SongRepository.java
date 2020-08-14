package com.baeldung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByNameLike(String name);

    List<Song> findByNameNotLike(String name);

    List<Song> findByNameStartingWith(String startingWith);

    List<Song> findByNameEndingWith(String endingWith);

    List<Song> findBySingerContaining(String singer);
}
