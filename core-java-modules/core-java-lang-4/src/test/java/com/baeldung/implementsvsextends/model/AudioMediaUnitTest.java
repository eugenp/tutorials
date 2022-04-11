package com.baeldung.implementsvsextends.media.model;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class AudioMediaUnitTest {

    @Test
    public void givenAudioMediaInstance_whenCheckedType_thenIsInstanceOfMedia() {
        AudioMedia audioMedia = new AudioMedia();
        Assert.assertThat(audioMedia, CoreMatchers.<AudioMedia>instanceOf(Media.class));
    }
}