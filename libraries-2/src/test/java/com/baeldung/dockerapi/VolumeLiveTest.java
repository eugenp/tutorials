package com.baeldung.dockerapi;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateVolumeResponse;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.command.ListVolumesResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import org.hamcrest.MatcherAssert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class VolumeLiveTest {

    private static DockerClient dockerClient;

    @BeforeClass
    public static void setup() {
        dockerClient = DockerClientBuilder.getInstance().build();
    }

    @Test
    public void whenListingVolumes_thenSizeMustBeGreaterThanZero() {

        // when
        ListVolumesResponse volumesResponse = dockerClient.listVolumesCmd().exec();

        // then
        List<InspectVolumeResponse> volumes = volumesResponse.getVolumes();
        assertThat(volumes.size(), is(greaterThan(0)));
    }

    @Test
    public void givenVolumes_whenInspectingVolume_thenReturnNonNullResponse() {

        // given
        ListVolumesResponse volumesResponse = dockerClient.listVolumesCmd().exec();
        List<InspectVolumeResponse> volumes = volumesResponse.getVolumes();
        InspectVolumeResponse volume = volumes.get(0);

        // when
        InspectVolumeResponse volumeResponse = dockerClient.inspectVolumeCmd(volume.getName()).exec();

        // then
        assertThat(volumeResponse, is(not(null)));
    }

    @Test
    public void whenCreatingUnnamedVolume_thenGetVolumeId() {

        // when
        CreateVolumeResponse unnamedVolume = dockerClient.createVolumeCmd().exec();

        // then
        MatcherAssert.assertThat(unnamedVolume.getName(), is(not(null)));
    }

    @Test
    public void whenCreatingNamedVolume_thenGetVolumeId() {

        // when
        CreateVolumeResponse namedVolume = dockerClient.createVolumeCmd().withName("myNamedVolume").exec();

        // then
        MatcherAssert.assertThat(namedVolume.getName(), is(not(null)));
    }

    @Test
    public void whenGettingNamedVolume_thenRemove() throws InterruptedException {

        // when
        CreateVolumeResponse namedVolume = dockerClient.createVolumeCmd().withName("anotherNamedVolume").exec();

        // then
        Thread.sleep(4000);
        dockerClient.removeVolumeCmd(namedVolume.getName()).exec();
    }
}
