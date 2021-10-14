package org.cloud.app.domain.port;

import org.cloud.app.domain.data.CloudTechDto;

import java.util.List;

public interface CloudTechPersistencePort {

    void addCloudTech(CloudTechDto cloudTechDto);

    void removeCloudTech(CloudTechDto cloudTechDto);

    void updateCloudTech(CloudTechDto cloudTechDto);

    List<CloudTechDto> getAllCloudTech();

    CloudTechDto getCloudTechById(Long cloudTechId);
}
