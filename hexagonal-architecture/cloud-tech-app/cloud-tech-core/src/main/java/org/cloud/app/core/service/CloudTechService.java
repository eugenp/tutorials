package org.cloud.app.core.service;

import org.cloud.app.domain.data.CloudTechDto;

import java.util.List;

public interface CloudTechService {

    void addCloudTech(CloudTechDto cloudTechDto);

    void removeCloudTech(CloudTechDto cloudTechDto);

    void updateCloudTech(CloudTechDto cloudTechDto);

    List<CloudTechDto> getAllCloudTech();

    CloudTechDto getCloudTechById(Long cloudTechId);

}



