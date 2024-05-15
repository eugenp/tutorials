package com.baeldung.implementsvsextends.media.model;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class VideoMediaUnitTest {

    @Test
    public void givenVideoMediaInstance_whenCheckedType_thenIsInstanceOfMedia() {
        VideoMedia videoMedia = new VideoMedia();
        Assert.assertThat(videoMedia, CoreMatchers.<VideoMedia>instanceOf(Media.class));
    }
}