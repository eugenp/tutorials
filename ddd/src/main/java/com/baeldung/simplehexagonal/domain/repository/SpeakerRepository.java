package com.baeldung.simplehexagonal.domain.repository;

import java.util.List;

import com.baeldung.simplehexagonal.domain.Speaker;

public interface SpeakerRepository {

    List<Speaker> findAll();

    Speaker findById(Long id);

    Speaker save(Speaker Speaker);

    void deleteById(Long id);

}
