package com.baeldung.hexagonalarchitecture.ports;

import com.baeldung.hexagonalarchitecture.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepositoryPort extends JpaRepository<Album, Long> {

    Album save(Album s);

    List<Album> findAll();

}
