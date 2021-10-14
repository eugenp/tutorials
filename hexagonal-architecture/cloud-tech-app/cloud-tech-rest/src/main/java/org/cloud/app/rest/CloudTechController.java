package org.cloud.app.rest;

import org.cloud.app.domain.data.CloudTechDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CloudTechController {

    @PostMapping("/cloudTech")
    ResponseEntity<Void> addCloudTech(@RequestBody CloudTechDto cloudTechDto);

    @DeleteMapping("/cloudTech")
    ResponseEntity<String> removeCloudTech(@RequestBody CloudTechDto cloudTechDto);

    @PutMapping("/cloudTech")
    ResponseEntity<String> updateCloudTech(@RequestBody CloudTechDto cloudTechDto);

    @GetMapping("/cloudTech/{cloudTechId}")
    ResponseEntity<CloudTechDto> getCloudTechById(@PathVariable Long cloudTechId);

    @GetMapping("/cloudTech")
    ResponseEntity<List<CloudTechDto>> getCloudTech();
}
