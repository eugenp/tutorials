package org.cloud.app.core.service;

import org.cloud.app.domain.data.CloudTechDto;
import org.cloud.app.domain.port.CloudTechPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CloudTechServiceImpl implements CloudTechService {

    private final CloudTechPersistencePort cloudTechPersistencePort;

    public CloudTechServiceImpl(CloudTechPersistencePort cloudTechPersistencePort) {
        this.cloudTechPersistencePort = cloudTechPersistencePort;
    }

    @Override
    public void addCloudTech(CloudTechDto cloudTechDto) {
        cloudTechPersistencePort.addCloudTech(cloudTechDto);
    }

    @Override
    @Transactional
    public void removeCloudTech(CloudTechDto cloudTechDto) {
        cloudTechPersistencePort.removeCloudTech(cloudTechDto);
    }

    @Override
    public void updateCloudTech(CloudTechDto cloudTechDto) {
        cloudTechPersistencePort.updateCloudTech(cloudTechDto);
    }

    @Override
    public List<CloudTechDto> getAllCloudTech() {
        return cloudTechPersistencePort.getAllCloudTech();
    }

    @Override
    public CloudTechDto getCloudTechById(Long cloudTechId) {
        return cloudTechPersistencePort.getCloudTechById(cloudTechId);
    }





}



