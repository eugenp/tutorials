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

public class CollectorUtilsUnitTest {

    @Test
    public void givenIntegerStream_whenCollectOnMaxByProjection_shouldReturnOptionalMaxValue() {
        Stream<String> integerStream = Stream.of("a", "bb", "ccc", "1");

        Optional<String> max = integerStream.collect(maxBy(String::length));

        assertThat(max.get(), is("ccc"));
    }

    @Test
    public void givenIntegerStream_whenCollectOnMinByProjection_shouldReturnOptionalMinValue() {
        Stream<String> integerStream = Stream.of("abc", "bb", "ccc", "1");

        Optional<String> max = integerStream.collect(minBy(String::length));

        assertThat(max.get(), is("1"));
    }

    @Test
    public void givenEmptyStream_withCollectorUnique_shouldReturnEmpty() {
        assertThat(Stream.empty().collect(CollectorUtils.unique()), equalTo(Optional.empty()));
    }

    @Test
    public void givenIntegerStream_withCollectorUnique_shouldReturnUniqueValue() {
        assertThat(Stream.of(1, 2, 3).filter(i -> i > 2).collect(CollectorUtils.unique()), equalTo(Optional.of(3)));
    }

    @Test
    public void givenIntegerStream_withUniqueNullable_shouldReturnUniqueValue() {
        assertThat(Stream.of(1, 2, 3).filter(i -> i > 2).collect(CollectorUtils.uniqueNullable()), equalTo(3));
    }

    @Test(expected = NonUniqueValueException.class)
    public void givenIntegerStream_withCollectorUnique_shouldThrowNonUniqueValueException() {
        Stream.of(1, 2, 3).filter(i -> i > 1).collect(CollectorUtils.unique());
    }
}
