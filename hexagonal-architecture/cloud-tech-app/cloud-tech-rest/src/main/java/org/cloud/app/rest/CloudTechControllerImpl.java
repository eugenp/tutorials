package org.cloud.app.rest;

import lombok.extern.slf4j.Slf4j;
import org.cloud.app.core.service.CloudTechService;
import org.cloud.app.domain.data.CloudTechDto;
import org.cloud.app.domain.exception.CloudTechNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
public class CloudTechControllerImpl implements CloudTechController {

    private final CloudTechService cloudTechService;

    private final Random random = new Random();

    public CloudTechControllerImpl(CloudTechService cloudTechService) {
        this.cloudTechService = cloudTechService;
    }

    @Override
    public ResponseEntity<Void> addCloudTech(CloudTechDto cloudTechDto) {
        cloudTechService.addCloudTech(cloudTechDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> removeCloudTech(CloudTechDto cloudTechDto) {
        cloudTechService.removeCloudTech(cloudTechDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateCloudTech(CloudTechDto cloudTechDto) {
        cloudTechService.updateCloudTech(cloudTechDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CloudTechDto> getCloudTechById(Long cloudTechId) {
        try {
            return new ResponseEntity<>(cloudTechService.getCloudTechById(cloudTechId), HttpStatus.OK);
        } catch (CloudTechNotFoundException ex) {
            log.error("Error!", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<CloudTechDto>> getCloudTech() {
        return new ResponseEntity<>(cloudTechService.getAllCloudTech(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CloudTechDto> getRandomLyric() {
        final List<CloudTechDto> allCloudTech = cloudTechService.getAllCloudTech();
        final int size = allCloudTech.size();
        return new ResponseEntity<>(allCloudTech.get(random.nextInt(size)), HttpStatus.OK);
    }
}
