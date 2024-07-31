package com.baeldung.filetomap;

import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FileToHashMapUnitTest {

    private String filePath;

    private static final Map<String, String> EXPECTED_MAP_DISCARD = Stream.of(new String[][]{
        {"title", "The Lord of the Rings: The Return of the King"},
        {"director", "Peter Jackson"},
        {"actor", "Sean Astin"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Map<String, String> EXPECTED_MAP_OVERWRITE = Stream.of(new String[][]{
        {"title", "The Lord of the Rings: The Return of the King"},
        {"director", "Peter Jackson"},
        {"actor", "Ian McKellen"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Map<String, List<String>> EXPECTED_MAP_AGGREGATE = Stream.of(new String[][]{
        {"title", "The Lord of the Rings: The Return of the King"},
        {"director", "Peter Jackson"},
        {"actor", "Sean Astin", "Ian McKellen"}
    }).collect(Collectors.toMap(arr -> arr[0], arr -> Arrays.asList(Arrays.copyOfRange(arr, 1, arr.length))));

    @Before
    public void setPath() throws URISyntaxException {
        if (filePath == null) {
            filePath = Paths.get(ClassLoader.getSystemResource("filetomap/theLordOfRings.txt").toURI()).toString();
        }
    }

    @Test
    public void givenInputFile_whenInvokeByBufferedReaderPriorToJava8_shouldGetExpectedMap() {
        Map<String, String> mapOverwrite = FileToHashMap.byBufferedReader(filePath, FileToHashMap.DupKeyOption.OVERWRITE);
        Map<String, String> mapDiscard = FileToHashMap.byBufferedReader(filePath, FileToHashMap.DupKeyOption.DISCARD);
        assertThat(mapOverwrite).isEqualTo(EXPECTED_MAP_OVERWRITE);
        assertThat(mapDiscard).isEqualTo(EXPECTED_MAP_DISCARD);
    }

    @Test
    public void givenInputFile_whenInvokeByStream_shouldGetExpectedMap() {
        Map<String, String> mapOverwrite = FileToHashMap.byStream(filePath, FileToHashMap.DupKeyOption.OVERWRITE);
        Map<String, String> mapDiscard = FileToHashMap.byStream(filePath, FileToHashMap.DupKeyOption.DISCARD);
        assertThat(mapOverwrite).isEqualTo(EXPECTED_MAP_OVERWRITE);
        assertThat(mapDiscard).isEqualTo(EXPECTED_MAP_DISCARD);
    }

    @Test
    public void givenInputFile_whenInvokeAggregateByKeys_shouldGetExpectedMap() {
        Map<String, List<String>> mapAgg = FileToHashMap.aggregateByKeys(filePath);
        assertThat(mapAgg).isEqualTo(EXPECTED_MAP_AGGREGATE);
    }
}
