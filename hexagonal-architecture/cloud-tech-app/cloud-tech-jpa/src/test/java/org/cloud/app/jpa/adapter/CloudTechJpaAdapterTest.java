package org.cloud.app.jpa.adapter;

import org.cloud.app.domain.data.CloudTechDto;
import org.cloud.app.domain.exception.CloudTechNotFoundException;
import org.cloud.app.domain.port.CloudTechPersistencePort;
import org.cloud.app.jpa.model.CloudTechEntity;
import org.cloud.app.jpa.repository.CloudTechRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        CloudTechJpaAdapter.class,
        CloudTechPersistencePort.class})
public class CloudTechJpaAdapterTest {

    private static final String TEST_CLOUD = "AWS";
    private static final String TEST_CLOUD_2 = "AZURE";
    private static final String TEST_CLOUDTECH = "AWS EC2";
    private static final String TEST_CLOUDTECH_2 = "AWS EC2";
    private static final String TEST_CLOUDTECH_3 = "AZURE ML";

    @Autowired
    private CloudTechPersistencePort cloudTechPersistencePort;

    @MockBean
    private CloudTechRepository mockCloudTechRepository;

    @Captor
    private ArgumentCaptor<CloudTechEntity> cloudTechEntityArgumentCaptor;

    @Test
    void givenCloudTech_whenAddCloudTech_thenEntityIsPortedToRepository() {
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();

        cloudTechPersistencePort.addCloudTech(testCloudTechDto);

        verify(mockCloudTechRepository, only()).save(cloudTechEntityArgumentCaptor.capture());
        final CloudTechEntity cloudTechEntity = cloudTechEntityArgumentCaptor.getValue();
        assertThat(cloudTechEntity.getCloud()).isEqualTo(TEST_CLOUD);
        assertThat(cloudTechEntity.getCloudTech()).isEqualTo(TEST_CLOUDTECH);
    }

    @Test
    void givenCloudTech_whenRemoveCloudTech_thenEntityRemovalIsPortedToRepository() {
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();

        cloudTechPersistencePort.removeCloudTech(testCloudTechDto);

        verify(mockCloudTechRepository, only()).deleteAllByCloud(testCloudTechDto.getCloud());
    }

    @Test
    void givenCallToAllCloudTechs_whenNoParams_thenFindAllIsPortedToRepository() {
        final CloudTechEntity testCloudTech = CloudTechEntity.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();
        final List<CloudTechEntity> testListCloudTechs = Collections.singletonList(testCloudTech);
        when(mockCloudTechRepository.findAll()).thenReturn(testListCloudTechs);

        final List<CloudTechDto> allCloudTechDtos = cloudTechPersistencePort.getAllCloudTech();

        verify(mockCloudTechRepository, only()).findAll();
        assertThat(allCloudTechDtos).hasSize(1);
        final CloudTechDto cloudTechDto = allCloudTechDtos.get(0);
        assertThat(cloudTechDto.getCloud()).isEqualTo(TEST_CLOUD);
        assertThat(cloudTechDto.getCloudTech()).isEqualTo(TEST_CLOUDTECH);
    }

    @Test
    void givenArtisId_whenCallingGetCloudTechById_thenFindByIdToRepository() {
        final CloudTechEntity testCloudTech = CloudTechEntity.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();
        when(mockCloudTechRepository.findById(1L)).thenReturn(Optional.of(testCloudTech));

        final CloudTechDto cloudTechDtoById = cloudTechPersistencePort.getCloudTechById(1L);

        verify(mockCloudTechRepository, only()).findById(1L);
        assertThat(cloudTechDtoById.getCloud()).isEqualTo(TEST_CLOUD);
        assertThat(cloudTechDtoById.getCloudTech()).isEqualTo(TEST_CLOUDTECH);
    }

    @Test
    void givenUnexistingArtisId_whenCallingGetCloudTechById_thenFindByIdToRepositoryFails() {
        when(mockCloudTechRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CloudTechNotFoundException.class, () -> cloudTechPersistencePort.getCloudTechById(1L));
    }

    @Test
    void givenAnExistingCloud_whenUpdateCloudTech_thenUpdateCloudTech() {
        final CloudTechEntity testCloudTech = CloudTechEntity.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH_2)
                .build();
        when(mockCloudTechRepository.findByCloud(TEST_CLOUD)).thenReturn(testCloudTech);

        cloudTechPersistencePort.updateCloudTech(testCloudTechDto);

        verify(mockCloudTechRepository, times(1)).findByCloud(TEST_CLOUD);
        verify(mockCloudTechRepository, times(1)).save(cloudTechEntityArgumentCaptor.capture());
        verifyNoMoreInteractions(mockCloudTechRepository);
        final CloudTechEntity cloudTechEntity = cloudTechEntityArgumentCaptor.getValue();
        assertThat(cloudTechEntity).isNotNull();
        assertThat(cloudTechEntity.getCloud()).isEqualTo(TEST_CLOUD);
        assertThat(cloudTechEntity.getCloudTech()).isEqualTo(TEST_CLOUDTECH_2);
    }

    @Test
    void givenAnExistingCloudTech_whenUpdateCloudTech_thenUpdateCloud() {
        final CloudTechEntity testCloudTech = CloudTechEntity.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH_3)
                .build();
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD_2)
                .cloudTech(TEST_CLOUDTECH_3)
                .build();
        when(mockCloudTechRepository.findByCloudTech(TEST_CLOUDTECH_3)).thenReturn(testCloudTech);

        cloudTechPersistencePort.updateCloudTech(testCloudTechDto);

        verify(mockCloudTechRepository, times(1)).findByCloud(TEST_CLOUD_2);
        verify(mockCloudTechRepository, times(1)).findByCloudTech(TEST_CLOUDTECH_3);
        verify(mockCloudTechRepository, times(1)).save(cloudTechEntityArgumentCaptor.capture());
        verifyNoMoreInteractions(mockCloudTechRepository);
        final CloudTechEntity cloudTechEntity = cloudTechEntityArgumentCaptor.getValue();
        assertThat(cloudTechEntity).isNotNull();
        assertThat(cloudTechEntity.getCloud()).isEqualTo(TEST_CLOUD_2);
        assertThat(cloudTechEntity.getCloudTech()).isEqualTo(TEST_CLOUDTECH_3);
    }
}