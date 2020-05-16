package com.baeldung.dockerapi;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class ImageLiveTest {

    private static DockerClient dockerClient;

    @BeforeClass
    public static void setup() {
        dockerClient = DockerClientBuilder.getInstance().build();
    }

    @Test
    public void whenListingImages_thenReturnNonEmptyList() {

        // when
        List<Image> images = dockerClient.listImagesCmd().exec();

        // then
        assertThat(images.size(), is(not(0)));
    }

    @Test
    public void whenListingImagesWithIntermediateImages_thenReturnNonEmptyList() {

        // when
        List<Image> images = dockerClient.listImagesCmd().withShowAll(true).exec();

        // then
        assertThat(images.size(), is(not(0)));
    }

    @Test
    public void whenListingDanglingImages_thenReturnNonNullList() {

        // when
        List<Image> images = dockerClient.listImagesCmd().withDanglingFilter(true).exec();

        // then
        assertThat(images, is(not(null)));
    }

    @Test
    public void whenBuildingImage_thenMustReturnImageId() {

        // when
        String imageId = dockerClient.buildImageCmd().withDockerfile(new File("src/test/resources/dockerapi/Dockerfile")).withPull(true).withNoCache(true).withTag("alpine:git").exec(new BuildImageResultCallback()).awaitImageId();

        // then
        assertThat(imageId, is(not(null)));
    }

    @Test
    public void givenListOfImages_whenInspectImage_thenMustReturnObject() {

        // given
        List<Image> images = dockerClient.listImagesCmd().exec();
        Image image = images.get(0);

        // when
        InspectImageResponse imageResponse = dockerClient.inspectImageCmd(image.getId()).exec();

        // then
        MatcherAssert.assertThat(imageResponse.getId(), Is.is(image.getId()));
    }

    @Test
    public void givenListOfImages_whenTagImage_thenListMustIncrement() {

        // given
        List<Image> images = dockerClient.listImagesCmd().exec();
        Image image = images.get(0);

        // when
        dockerClient.tagImageCmd(image.getId(), "baeldung/alpine", "3.6.v2").exec();

        // then
        List<Image> imagesNow = dockerClient.listImagesCmd().exec();
        assertThat(imagesNow.size(), is(greaterThan(images.size())));
    }

    public void pushingAnImage() throws InterruptedException {

        dockerClient.pushImageCmd("baeldung/alpine").withTag("3.6.v2").exec(new PushImageResultCallback()).awaitCompletion(90, TimeUnit.SECONDS);
    }

    @Test
    public void whenPullingImage_thenImageListNotEmpty() throws InterruptedException {

        // when
        dockerClient.pullImageCmd("alpine").withTag("latest").exec(new PullImageResultCallback()).awaitCompletion(30, TimeUnit.SECONDS);

        // then
        List<Image> images = dockerClient.listImagesCmd().exec();
        assertThat(images.size(), is(not(0)));
    }

    @Test
    public void whenRemovingImage_thenImageListDecrease() {

        // when
        List<Image> images = dockerClient.listImagesCmd().exec();
        Image image = images.get(0);
        dockerClient.removeImageCmd(image.getId()).exec();

        // then
        List<Image> imagesNow = dockerClient.listImagesCmd().exec();
        assertThat(imagesNow.size(), is(lessThan(images.size())));
    }

    @Test
    public void whenSearchingImage_thenMustReturn25Items() {

        // when
        List<SearchItem> items = dockerClient.searchImagesCmd("Java").exec();

        // then
        assertThat(items.size(), is(25));
    }
}
