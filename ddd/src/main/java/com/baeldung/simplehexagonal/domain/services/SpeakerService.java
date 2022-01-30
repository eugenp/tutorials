package com.baeldung.simplehexagonal.domain.services;

import java.util.List;

import com.baeldung.simplehexagonal.domain.Speaker;

public interface SpeakerService {

    List<Speaker> findAll();

    Speaker get(Long id);

    Speaker save(Speaker speaker);

    void delete(Long id);

    Speaker update(Long id, Speaker speaker);

}