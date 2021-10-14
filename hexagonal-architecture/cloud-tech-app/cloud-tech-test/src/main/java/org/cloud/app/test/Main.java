package org.cloud.app.test;

import org.cloud.app.core.service.CloudTechServiceImpl;
import org.cloud.app.core.service.CloudTechService;
import org.cloud.app.domain.data.CloudTechDto;
import org.cloud.app.domain.port.CloudTechPersistencePort;

import java.util.Collections;
import java.util.List;

/**
 * This is just a test application. The goal is not to use test frameworks. The goal is just to show how easy it is to implement an adapter that is specifically designed to test nothing else but the core of the application.
 * <p>
 * Please run this with -ea JVM command.
 */
public class Main {

    public static void main(String[] args) {
        final CloudTechService cloudTechService = new CloudTechServiceImpl(createMockCloudTechPersistencePort());
        final CloudTechDto cloudTechDto = CloudTechDto.builder()
                .cloud("AWS")
                .cloudTech("AWS EC2")
                .build();
        cloudTechService.addCloudTech(cloudTechDto);
        cloudTechService.updateCloudTech(cloudTechDto);
        cloudTechService.removeCloudTech(cloudTechDto);
        final List<CloudTechDto> allCloudTechDtos = cloudTechService.getAllCloudTech();
        final CloudTechDto cloudTechDtoById = cloudTechService.getCloudTechById(1L);
        assert allCloudTechDtos.size() == 1;
        final CloudTechDto cloudTechDto1 = allCloudTechDtos.get(0);
        assert cloudTechDto1
                .getCloud()
                .equals("AWS");
        assert cloudTechDto1
                .getCloudTech()
                .equals("AWS Athena");
        assert cloudTechDtoById.getCloud()
                .equals("AWS");
        assert cloudTechDtoById.getCloudTech()
                .equals("AWS EC2");
    }

    private static CloudTechPersistencePort createMockCloudTechPersistencePort() {
        return new CloudTechPersistencePort() {
            @Override
            public void addCloudTech(CloudTechDto cloudTechDto) {

            }

            @Override
            public void removeCloudTech(CloudTechDto cloudTechDto) {

            }

            @Override
            public void updateCloudTech(CloudTechDto cloudTechDto) {

            }

            @Override
            public List<CloudTechDto> getAllCloudTech() {
                final CloudTechDto cloudTechDto = CloudTechDto.builder()
                        .cloud("AWS")
                        .cloudTech("AWS RDS")
                        .build();
                return Collections.singletonList(cloudTechDto);
            }

            @Override
            public CloudTechDto getCloudTechById(Long cloudTechId) {
                return CloudTechDto.builder()
                        .cloud("AWS")
                        .cloudTech("AWS Automate")
                        .build();
            }
        };
    }
}
