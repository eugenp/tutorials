package com.baeldung.spring.jpa.guide.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.spring.jpa.guide.model.Publishers;
import com.baeldung.spring.jpa.guide.repository.PublisherRepository;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publishers save(Publishers publishers) {
        return publisherRepository.save(publishers);
    }

    public List<Publishers> findAll() {
        return publisherRepository.findAll();
    }

    public Publishers findById(int id) {
        return publisherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publisher not found"));
    }

    public void delete(int id) {
        publisherRepository.deleteById(id);
    }

    public Publishers update(Publishers publishers) {
        return publisherRepository.save(publishers);
    }

    public List<Publishers> findAllByLocation(String location) {
        return publisherRepository.findAllByLocation(location);
    }

    public List<Publishers> findPublishersWithMinJournalsInLocation(int minJournals, String location) {
        return publisherRepository.findPublishersWithMinJournalsInLocation(minJournals, location);
    }
}
