package com.baeldung.dockerapi;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.Network.Ipam;
import com.github.dockerjava.core.DockerClientBuilder;
import org.hamcrest.MatcherAssert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class NetworkLiveTest {

    private static DockerClient dockerClient;

    @BeforeClass
    public static void setup() {
        dockerClient = DockerClientBuilder.getInstance().build();
    }

    @Test
    @Ignore("temporarily")
    public void whenListingNetworks_thenSizeMustBeGreaterThanZero() {

        // when
        List<Network> networks = dockerClient.listNetworksCmd().exec();

        // then
        assertThat(networks.size(), is(greaterThan(0)));
    }

    @Test
    public void whenCreatingNetwork_thenRetrieveResponse() {

        // when
        CreateNetworkResponse networkResponse = dockerClient.createNetworkCmd().withName("baeldungDefault").withDriver("bridge").exec();

        // then
        assertThat(networkResponse, is(not(null)));
    }

    @Test
    public void whenCreatingAdvanceNetwork_thenRetrieveResponse() {

        // when
        CreateNetworkResponse networkResponse = dockerClient.createNetworkCmd().withName("baeldungAdvanced").withIpam(new Ipam().withConfig(new Ipam.Config().withSubnet("172.36.0.0/16").withIpRange("172.36.5.0/24"))).withDriver("bridge").exec();

        // then
        assertThat(networkResponse, is(not(null)));
    }

    @Test
    public void whenInspectingNetwork_thenSizeMustBeGreaterThanZero() {

        // when
        String networkName = "bridge";
        Network network = dockerClient.inspectNetworkCmd().withNetworkId(networkName).exec();

        // then
        MatcherAssert.assertThat(network.getName(), is(networkName));
    }

    @Test
    public void whenCreatingNetwork_thenRemove() throws InterruptedException {

        // when
        CreateNetworkResponse networkResponse = dockerClient.createNetworkCmd().withName("baeldungDefault").withDriver("bridge").exec();

        // then
        Thread.sleep(4000);
        dockerClient.removeNetworkCmd(networkResponse.getId()).exec();
    }
}
