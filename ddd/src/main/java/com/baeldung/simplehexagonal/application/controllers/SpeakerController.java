package com.baeldung.simplehexagonal.application.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.simplehexagonal.domain.Speaker;
import com.baeldung.simplehexagonal.domain.services.SpeakerService;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {

    private SpeakerService speakerService;

    @Autowired
    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    public List<Speaker> findAll() {
        return speakerService.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        return speakerService.get(id);
    }

    @PostMapping
    public Speaker create(@RequestBody final Speaker speaker) {
        return speakerService.save(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        speakerService.delete(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        Speaker currentSpeaker = speakerService.get(id);
        BeanUtils.copyProperties(speaker, currentSpeaker, "speaker_id");
        return speakerService.save(currentSpeaker);
    }

}
