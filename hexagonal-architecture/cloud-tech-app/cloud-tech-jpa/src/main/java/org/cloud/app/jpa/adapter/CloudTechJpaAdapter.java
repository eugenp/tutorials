package org.cloud.app.jpa.adapter;

import lombok.SneakyThrows;
import org.cloud.app.domain.data.CloudTechDto;
import org.cloud.app.domain.exception.CloudTechNotFoundException;
import org.cloud.app.domain.port.CloudTechPersistencePort;
import org.cloud.app.jpa.model.CloudTechEntity;
import org.cloud.app.jpa.repository.CloudTechRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class CloudTechJpaAdapter implements CloudTechPersistencePort {

    private CloudTechRepository cloudTechRepository;

    public CloudTechJpaAdapter(CloudTechRepository cloudTechRepository) {
        this.cloudTechRepository = cloudTechRepository;
    }

    @Override
    public void addCloudTech(CloudTechDto cloudTechDto) {
        final CloudTechEntity cloudTechEntity = getCloudTechEntity(cloudTechDto);
        cloudTechRepository.save(cloudTechEntity);
    }

    @Override
    public void removeCloudTech(CloudTechDto cloudTechDto) {
        cloudTechRepository.deleteAllByCloud(cloudTechDto.getCloud());
    }

    @Override
    public void updateCloudTech(CloudTechDto cloudTechDto) {
        final CloudTechEntity byCloud = cloudTechRepository.findByCloud(cloudTechDto.getCloud());
        if (Objects.nonNull(byCloud)) {
            byCloud.setCloudTech(cloudTechDto.getCloudTech());
            cloudTechRepository.save(byCloud);
        } else {
            final CloudTechEntity byCloudTech = cloudTechRepository.findByCloudTech(cloudTechDto.getCloudTech());
            if (Objects.nonNull(byCloudTech)) {
                byCloudTech.setCloud(cloudTechDto.getCloud());
                cloudTechRepository.save(byCloudTech);
            }
        }
    }

    @Override
    public List<CloudTechDto> getAllCloudTech() {
        return cloudTechRepository.findAll()
                .stream()
                .map(this::getCloudTech)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public CloudTechDto getCloudTechById(Long cloudTechId) {
        return getCloudTech(cloudTechRepository.findById(cloudTechId)
                .orElseThrow((Supplier<Throwable>) () -> new CloudTechNotFoundException(cloudTechId)));
    }

    private CloudTechEntity getCloudTechEntity(CloudTechDto cloudTechDto) {
        return CloudTechEntity.builder()
                .cloud(cloudTechDto.getCloud())
                .cloudTech(cloudTechDto.getCloudTech())
                .build();
    }

    private CloudTechDto getCloudTech(CloudTechEntity cloudTechEntity) {
        return CloudTechDto.builder()
                .cloud(cloudTechEntity.getCloud())
                .cloudTech(cloudTechEntity.getCloudTech())
                .build();
    }

}
