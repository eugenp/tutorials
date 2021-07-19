package com.baeldung.dockerapi;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DockerClientBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class ContainerLiveTest {

    private static DockerClient dockerClient;

    @BeforeClass
    public static void setup() {
        dockerClient = DockerClientBuilder.getInstance().build();
    }

    @Test
    public void whenListingRunningContainers_thenReturnNonEmptyList() {

        // when
        List<Container> containers = dockerClient.listContainersCmd().exec();

        // then
        assertThat(containers.size(), is(not(0)));
    }

    @Test
    public void whenListingExitedContainers_thenReturnNonEmptyList() {

        // when
        List<Container> containers = dockerClient.listContainersCmd().withShowSize(true).withShowAll(true).withStatusFilter("exited").exec();

        // then
        assertThat(containers.size(), is(not(0)));
    }

    @Test
    public void whenCreatingContainer_thenMustReturnContainerId() {

        // when
        CreateContainerResponse container = dockerClient.createContainerCmd("mongo:3.6").withCmd("--bind_ip_all").withName("mongo").withHostName("baeldung").withEnv("MONGO_LATEST_VERSION=3.6").withPortBindings(PortBinding.parse("9999:27017")).exec();

        // then
        MatcherAssert.assertThat(container.getId(), is(not(null)));
    }

    @Test
    public void whenHavingContainer_thenRunContainer() throws InterruptedException {

        // when
        CreateContainerResponse container = dockerClient.createContainerCmd("alpine:3.6").withCmd("sleep", "10000").exec();

        Thread.sleep(3000);
        // then
        dockerClient.startContainerCmd(container.getId()).exec();

        dockerClient.stopContainerCmd(container.getId()).exec();
    }

    @Test
    public void whenRunningContainer_thenStopContainer() throws InterruptedException {

        // when
        CreateContainerResponse container = dockerClient.createContainerCmd("alpine:3.6").withCmd("sleep", "10000").exec();

        Thread.sleep(3000);
        dockerClient.startContainerCmd(container.getId()).exec();

        // then
        dockerClient.stopContainerCmd(container.getId()).exec();
    }

    @Test
    public void whenRunningContainer_thenKillContainer() throws InterruptedException {

        // when
        CreateContainerResponse container = dockerClient.createContainerCmd("alpine:3.6").withCmd("sleep", "10000").exec();

        dockerClient.startContainerCmd(container.getId()).exec();

        Thread.sleep(3000);
        dockerClient.stopContainerCmd(container.getId()).exec();

        // then
        dockerClient.killContainerCmd(container.getId()).exec();
    }

    @Test
    public void whenHavingContainer_thenInspectContainer() {

        // when
        CreateContainerResponse container = dockerClient.createContainerCmd("alpine:3.6").withCmd("sleep", "10000").exec();

        // then
        InspectContainerResponse containerResponse = dockerClient.inspectContainerCmd(container.getId()).exec();

        MatcherAssert.assertThat(containerResponse.getId(), Is.is(container.getId()));
    }

    @Test
    public void givenContainer_whenCommittingContainer_thenMustReturnImageId() {

        // given
        CreateContainerResponse container = dockerClient.createContainerCmd("alpine:3.6").withCmd("sleep", "10000").exec();

        // when
        String imageId = dockerClient.commitCmd(container.getId()).withEnv("SNAPSHOT_YEAR=2018").withMessage("add git support").withCmd("sleep", "10000").withRepository("alpine").withTag("3.6.v2").exec();

        // then
        assertThat(imageId, is(not(null)));
    }

}
