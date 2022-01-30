package com.baeldung.simplehexagonal.infrastructure.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.simplehexagonal.domain.Speaker;
import com.baeldung.simplehexagonal.domain.repository.SpeakerRepository;

@Component
public class SpeakerRepositoryImpl implements SpeakerRepository {

    @Autowired
    private SpeakerJpaRepository speakerJpaRepository;

    @Override
    public List<Speaker> findAll() {
        return speakerJpaRepository.findAll()
            .stream()
            .map(SpeakerEntity::toSpeaker)
            .collect(Collectors.toList());
    }

    @Override
    public Speaker findById(Long id) {
        SpeakerEntity speakerEntity = speakerJpaRepository.getById(id);
        return speakerEntity.toSpeaker();
    }

    @Override
    public Speaker save(Speaker speaker) {
        return speakerJpaRepository.saveAndFlush(new SpeakerEntity(speaker))
            .toSpeaker();
    }

    public void deleteById(Long id) {
        speakerJpaRepository.deleteById(id);
    }

}
