package com.baeldung.partitioning;

import static com.baeldung.streams.partitioning.PartitionStream.partitionList;
import static com.baeldung.streams.partitioning.PartitionStream.partitionStream;
import static com.baeldung.streams.partitioning.PartitionStream.partitionUsingGuava;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.atIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class PartitionStreamsUnitTest {

    @Test
    void whenPartitionList_thenReturnThreeSubLists() {
        List<Integer> source = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        Stream<List<Integer>> result = partitionList(source, 3);

        assertThat(result)
          .containsExactlyInAnyOrder(
            List.of(1, 2, 3),
            List.of(4, 5, 6),
            List.of(7, 8)
          );
    }

    @Test
    void whenPartitionEmptyList_thenReturnEmptyStream() {
        Stream<List<Integer>> result = partitionList(Collections.emptyList(), 3);

        assertThat(result).isEmpty();
    }

    @Test
    void whenPartitionListWithNegativeBatchSize_thenThrowException() {
        assertThatThrownBy(() -> partitionList(List.of(1,2,3), -1))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Expected the batchSize to be greater than ZERO, actual value was: -1");
    }

    @Test
    void whenPartitionParallelStream_thenReturnThreeSubLists() {
        Stream<Integer> source = Stream.of(1, 2, 3, 4, 5, 6, 7, 8).parallel();

        List<List<Integer>> result = partitionStream(source, 3);

        assertThat(result)
          .hasSize(3)
          .satisfies(batch -> assertThat(batch).hasSize(3), atIndex(0))
          .satisfies(batch -> assertThat(batch).hasSize(3), atIndex(1))
          .satisfies(batch -> assertThat(batch).hasSize(2), atIndex(2));
    }

    @Test
    void whenPartitionEmptyParallelStream_thenReturnEmptyList() {
        Stream<Integer> source = Stream.<Integer>empty().parallel();

        List<List<Integer>> result = partitionStream(source, 3);

        assertThat(result).isEmpty();
    }

    @Test
    void whenPartitionParallelStreamWithGuava_thenReturnThreeSubLists() {
        Stream<Integer> source = Stream.of(1, 2, 3, 4, 5, 6, 7, 8).parallel();

        Iterable<List<Integer>> result = partitionUsingGuava(source, 3);

        assertThat(result)
          .map(ArrayList::new)
          .hasSize(3)
          .satisfies(batch -> assertThat(batch).asList().hasSize(3), atIndex(0))
          .satisfies(batch -> assertThat(batch).asList().hasSize(3), atIndex(1))
          .satisfies(batch -> assertThat(batch).asList().hasSize(2), atIndex(2));
    }
}
