package com.baeldung.absolutetorelative;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AbsoluteToRelativeUnitTest {

    // given - until using Paths, no need to create physical files
    private final Path pathOne = Paths.get("/baeldung/bar/one.txt");
    private final Path pathTwo = Paths.get("/baeldung/bar/two.txt");
    private final Path pathThree = Paths.get("/baeldung/foo/three.txt");

    private final URI uriOne = pathOne.toUri();
    private final URI uriTwo = pathTwo.toUri();
    private final URI uriThree = pathThree.toUri();

    @Test
    public void givenAbsolutePaths_whenRelativizePathOneToPathTwo_thenRelativeIsReturned() {
        Path result = pathOne.relativize(pathTwo);

        Assertions.assertThat(result)
                .isRelative()
                .isEqualTo(Paths.get("../two.txt"));
    }

    @Test
    public void givenAbsolutePaths_whenRelativizePathTwoToPathOne_thenRelativeIsReturned() {
        Path result = pathTwo.relativize(pathOne);

        Assertions.assertThat(result)
                .isRelative()
                .isEqualTo(Paths.get("../one.txt"));
    }

    @Test
    public void givenAbsolutePaths_whenRelativizePathOneParentToPathTwo_thenRelativeIsReturned() {
        Path result = pathOne.getParent().relativize(pathTwo);

        Assertions.assertThat(result)
                .isRelative()
                .isEqualTo(Paths.get("two.txt"));
    }

    @Test
    public void givenAbsolutePaths_whenRelativizePathOneToPathThree_thenRelativeIsReturned() {
        Path result = pathOne.relativize(pathThree);

        Assertions.assertThat(result)
                .isRelative()
                .isEqualTo(Paths.get("../../foo/three.txt"));
    }

    @Test
    public void givenAbsolutePaths_whenRelativizePathThreeToPathOne_thenRelativeIsReturned() {
        Path result = pathThree.relativize(pathOne);

        Assertions.assertThat(result)
                .isRelative()
                .isEqualTo(Paths.get("../../bar/one.txt"));
    }

    @Test
    public void givenAbsoluteURIs_whenRelativizeUriOneToUriTwo_thenAbsoluteIsReturned() {
        URI result = uriOne.relativize(uriTwo);

        Assertions.assertThat(result)
                .asString()
                .contains("/baeldung/bar/two.txt");
    }

    @Test
    public void givenAbsoluteURIs_whenRelativizeUriOneParentToUriTwo_thenRelativeIsReturned() {
        URI result = pathOne.getParent().toUri().relativize(uriTwo);

        Assertions.assertThat(result)
                .asString()
                .contains("two.txt");
    }

    @Test
    public void givenAbsoluteURIs_whenRelativizeUriOneParentToUriThree_thenAbsoluteIsReturned() {
        URI result = pathOne.getParent().toUri().relativize(uriThree);

        Assertions.assertThat(result)
                .asString()
                .contains("/baeldung/foo/three.txt");
    }

}
