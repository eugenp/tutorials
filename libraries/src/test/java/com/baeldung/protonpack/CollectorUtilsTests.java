package com.baeldung.protonpack;

import com.codepoetics.protonpack.collectors.CollectorUtils;
import com.codepoetics.protonpack.collectors.NonUniqueValueException;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.collectors.CollectorUtils.maxBy;
import static com.codepoetics.protonpack.collectors.CollectorUtils.minBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CollectorUtilsTests {

    @Test
    public void maxByWithProjectionAndDefaultComparer() {
        Stream<String> integerStream = Stream.of("a", "bb", "ccc", "1");

        Optional<String> max = integerStream.collect(maxBy(String::length));

        assertThat(max.get(), is("ccc"));
    }

    @Test
    public void minByWithProjectionAndDefaultComparer() {
        Stream<String> integerStream = Stream.of("abc", "bb", "ccc", "1");

        Optional<String> max = integerStream.collect(minBy(String::length));

        assertThat(max.get(), is("1"));
    }

    @Test
    public void returnsEmptyForEmptyStream() {
        assertThat(Stream
          .empty()
          .collect(CollectorUtils.unique()), equalTo(Optional.empty()));
    }

    @Test
    public void returnsUniqueItem() {
        assertThat(Stream
          .of(1, 2, 3)
          .filter(i -> i > 2)
          .collect(CollectorUtils.unique()), equalTo(Optional.of(3)));
    }

    @Test
    public void returnsUniqueNullableItem() {
        assertThat(Stream
          .of(1, 2, 3)
          .filter(i -> i > 2)
          .collect(CollectorUtils.uniqueNullable()), equalTo(3));
    }

    @Test(expected = NonUniqueValueException.class)
    public void throwsExceptionIfItemIsNotUnique() {
        Stream
          .of(1, 2, 3)
          .filter(i -> i > 1)
          .collect(CollectorUtils.unique());
    }
}
