package com.baeldung.spring.jpa.guide;

import com.baeldung.spring.jpa.guide.model.Publishers;
import com.baeldung.spring.jpa.guide.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/save")
    public ResponseEntity<Publishers> save(@RequestBody Publishers publishers) {
        Publishers savedPublisher = publisherService.save(publishers);
        return ResponseEntity.ok(savedPublisher);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Publishers>> findAll() {
        List<Publishers> publishersList = publisherService.findAll();
        return ResponseEntity.ok(publishersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publishers> findById(@PathVariable int id) {
        Publishers publisher = publisherService.findById(id);
        return ResponseEntity.ok(publisher);
    }

    @PutMapping("/update")
    public ResponseEntity<Publishers> update(@RequestBody Publishers publishers) {
        Publishers updatedPublisher = publisherService.update(publishers);
        return ResponseEntity.ok(updatedPublisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        publisherService.delete(id);
        return ResponseEntity.noContent()
            .build();
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Publishers>> findAllByLocation(@PathVariable String location) {
        List<Publishers> publishersList = publisherService.findAllByLocation(location);
        return ResponseEntity.ok(publishersList);
    }

    @GetMapping("/min-journals")
    public ResponseEntity<List<Publishers>> findPublishersWithMinJournalsInLocation(@RequestParam int minJournals, @RequestParam String location) {
        List<Publishers> publishersList = publisherService.findPublishersWithMinJournalsInLocation(minJournals, location);
        return ResponseEntity.ok(publishersList);
    }

}
