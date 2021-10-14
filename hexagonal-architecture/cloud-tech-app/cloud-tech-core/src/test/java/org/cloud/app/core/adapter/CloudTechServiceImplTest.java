package org.cloud.app.core.adapter;

import org.cloud.app.core.service.CloudTechServiceImpl;
import org.cloud.app.domain.data.CloudTechDto;
import org.cloud.app.domain.port.CloudTechPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CloudTechServiceImplTest {

    @InjectMocks
    private CloudTechServiceImpl cloudTechServiceImpl;

    @Mock
    private CloudTechPersistencePort cloudTechPersistencePort;

    @Mock
    private List<CloudTechDto> mockCloudTechDtoList;

    @Test
    public void givenCloudTech_whenAdd_thenAddPortCalled() {
        final CloudTechDto mockCloudTechDto = mock(CloudTechDto.class);

        cloudTechServicePort.addCloudTech(mockCloudTechDto);

        verify(cloudTechPersistencePort, only()).addCloudTech(mockCloudTechDto);
    }

    @Test
    public void givenCloudTech_whenRemove_thenRemovePortCalled() {
        final CloudTechDto mockCloudTechDto = mock(CloudTechDto.class);

        cloudTechServicePort.removeCloudTech(mockCloudTechDto);

        verify(cloudTechPersistencePort, only()).removeCloudTech(mockCloudTechDto);
    }


    @Test
    public void givenCloudTech_whenUpdate_thenUpdateCloudTechPortCalled() {
        final CloudTechDto mockCloudTechDto = mock(CloudTechDto.class);

        cloudTechServicePort.updateCloudTech(mockCloudTechDto);

        verify(cloudTechPersistencePort, only()).updateCloudTech(mockCloudTechDto);
    }

    @Test
    public void givenCallToAllCloudTech_whenNothingSpecified_thenGetAllCloudTechsPortCalled() {
        when(cloudTechPersistencePort.getAllCloudTech()).thenReturn(mockCloudTechDtoList);

        final List<CloudTechDto> allCloudTechDtos = cloudTechServicePort.getAllCloudTech();

        assertThat(allCloudTechDtos).isSameAs(mockCloudTechDtoList);
        verify(cloudTechPersistencePort, only()).getAllCloudTech();
    }

    @Test
    public void givenCloudTechId_whenGetCloudTechsById_thenGetCloudTechByIdPortCalled() {
        final Long testCloudTechId = 1L;
        final CloudTechDto mockCloudTechDto = mock(CloudTechDto.class);
        when(cloudTechPersistencePort.getCloudTechById(testCloudTechId)).thenReturn(mockCloudTechDto);

        final CloudTechDto cloudTechDto = cloudTechServicePort.getCloudTechById(testCloudTechId);

        assertThat(cloudTechDto).isSameAs(mockCloudTechDto);
        verify(cloudTechPersistencePort, only()).getCloudTechById(testCloudTechId);
    }

}