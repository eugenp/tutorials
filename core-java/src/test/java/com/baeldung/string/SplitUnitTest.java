package com.baeldung.string;

import com.google.common.base.Splitter;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SplitUnitTest {

    @Test
    public void givenString_whenSplit_thenReturnsArray_through_JavaLangString() {
        assertThat("peter,james,thomas".split(","))
          .containsExactly("peter", "james", "thomas");

        assertThat("car jeep scooter".split(" "))
          .containsExactly("car", "jeep", "scooter");

        assertThat("1-120-232323".split("-"))
          .containsExactly("1", "120", "232323");

        assertThat("192.168.1.178".split("\\."))
          .containsExactly("192", "168", "1", "178");

        assertThat("b a, e, l.d u, n g".split("\\s+|,\\s*|\\.\\s*"))
          .containsExactly("b", "a", "e", "l", "d", "u", "n", "g");
    }

    @Test
    public void givenString_whenSplit_thenReturnsArray_through_StringUtils() {
        StringUtils.split("car jeep scooter");

        assertThat(StringUtils.split("car jeep scooter"))
          .containsExactly("car", "jeep", "scooter");

        assertThat(StringUtils.split("car    jeep   scooter"))
          .containsExactly("car", "jeep", "scooter");

        assertThat(StringUtils.split("car:jeep:scooter", ":"))
          .containsExactly("car", "jeep", "scooter");

        assertThat(StringUtils.split("car.jeep.scooter", "."))
          .containsExactly("car", "jeep", "scooter");
    }

    @Test
    public void givenString_whenSplit_thenReturnsList_Splitter() {
        //given
        List<String> resultList = Splitter.on(',').trimResults().omitEmptyStrings().splitToList("car,jeep,,   scooter");

        assertThat(resultList)
          .containsExactly("car", "jeep", "scooter");
    }
}
