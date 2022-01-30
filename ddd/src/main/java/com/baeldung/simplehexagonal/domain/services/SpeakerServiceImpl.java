package com.baeldung.simplehexagonal.domain.services;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baeldung.simplehexagonal.domain.Speaker;
import com.baeldung.simplehexagonal.domain.repository.SpeakerRepository;

public class SpeakerServiceImpl implements SpeakerService {

    private SpeakerRepository speakerRepository;

    public SpeakerServiceImpl(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    public Speaker get(Long id) {
        return speakerRepository.findById(id);
    }

    public Speaker save(Speaker speaker) {
        return speakerRepository.save(speaker);
    }

    public void delete(Long id) {
        speakerRepository.deleteById(id);
    }

    public Speaker update(Long id, Speaker speaker) {
        Speaker currentSpeaker = speakerRepository.findById(id);
        BeanUtils.copyProperties(speaker, currentSpeaker, "speaker_id");
        return speakerRepository.save(currentSpeaker);
    }
}
