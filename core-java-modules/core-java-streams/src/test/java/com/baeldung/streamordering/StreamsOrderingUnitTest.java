package com.baeldung.streamordering;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class StreamsOrderingUnitTest {

  Logger logger = Logger.getLogger( StreamsOrderingUnitTest.class.getName());

  @Before
  public void setUp() throws Exception {
    logger.setLevel(Level.ALL);
  }

  @Test
  public void givenTwoCollections_whenStreamed_thenCheckOutputDifferent(){

    List<String> list = Arrays.asList("B", "A", "C", "D", "F");
    Set<String> set = new TreeSet<>(Arrays.asList("B", "A", "C", "D", "F"));

    Object[] listOutput = list.stream().toArray();
    Object[] setOutput = set.stream().toArray();

    assertEquals("[B, A, C, D, F]", Arrays.toString(listOutput));
    assertEquals("[A, B, C, D, F]", Arrays.toString(setOutput));

  }

  @Test
  public void givenTwoCollections_whenStreamedInParallel_thenCheckOutputDifferent(){

    List<String> list = Arrays.asList("B", "A", "C", "D", "F");
    Set<String> set = new TreeSet<>(Arrays.asList("B", "A", "C", "D", "F"));

    Object[] listOutput = list.stream().parallel().toArray();
    Object[] setOutput = set.stream().parallel().toArray();

    assertEquals("[B, A, C, D, F]", Arrays.toString(listOutput));
    assertEquals("[A, B, C, D, F]", Arrays.toString(setOutput));

  }



  @Test
  public void givenOrderedInput_whenUnorderedAndOrderedCompared_thenCheckUnorderedOutputChanges(){
    Set<Integer> set = new TreeSet<>(
      Arrays.asList(-9, -5, -4, -2, 1, 2, 4, 5, 7, 9, 12, 13, 16, 29, 23, 34, 57, 68, 90, 102, 230));

      Object[] orderedArray = set.stream()
        .parallel()
        .limit(5)
        .toArray();
      Object[] unorderedArray = set.stream()
        .unordered()
        .parallel()
        .limit(5)
        .toArray();

     logger.info(Arrays.toString(orderedArray));
     logger.info(Arrays.toString(unorderedArray));
  }


  @Test
  public void givenUnsortedStreamInput_whenStreamSorted_thenCheckOrderChanged(){

    List<Integer> list = Arrays.asList(-3,10,-4,1,3);

    Object[] listOutput = list.stream().toArray();
    Object[] listOutputSorted = list.stream().sorted().toArray();

    assertEquals("[-3, 10, -4, 1, 3]", Arrays.toString(listOutput));
    assertEquals("[-4, -3, 1, 3, 10]", Arrays.toString(listOutputSorted));

  }

  @Test
  public void givenUnsortedStreamInput_whenStreamDistinct_thenShowTimeTaken(){
    long start, end;
    start = System.currentTimeMillis();
    IntStream.range(1,1_000_000).unordered().parallel().distinct().toArray();
    end = System.currentTimeMillis();
    System.out.println(String.format("Time taken when unordered: %d ms", (end - start)));
  }


  @Test
  public void givenSameCollection_whenStreamTerminated_thenCheckEachVsEachOrdered(){

    List<String> list = Arrays.asList("B", "A", "C", "D", "F");

    list.stream().parallel().forEach(e -> logger.log(Level.INFO, e));
    list.stream().parallel().forEachOrdered(e -> logger.log(Level.INFO, e));

  }

  @Test
  public void givenSameCollection_whenStreamCollected_thenCheckOutput(){

    List<String> list = Arrays.asList("B", "A", "C", "D", "F");

    List<String> collectionList = list.stream().parallel().collect(Collectors.toList());
    Set<String> collectionSet = list.stream().parallel().collect(Collectors.toCollection(TreeSet::new));

    assertEquals("[B, A, C, D, F]", collectionList.toString());
    assertEquals("[A, B, C, D, F]", collectionSet.toString());

  }


  @Test
  public void givenListIterationOrder_whenStreamCollectedToMap_thenCeckOrderChanged() {
    List<String> list = Arrays.asList("A", "BB", "CCC");

    Map<String, Integer> hashMap = list.stream().collect(Collectors.toMap(Function.identity(), String::length));

    Object[] keySet = hashMap.keySet().toArray();

    assertEquals("[BB, A, CCC]", Arrays.toString(keySet));

  }

  @Test
  public void givenListIteration_whenStreamCollectedtoHashMap_thenCheckOrderMaintained() {
    List<String> list = Arrays.asList("A", "BB", "CCC", "CCC");

    Map<String, Integer> linkedHashMap = list.stream().collect(Collectors.toMap(
      Function.identity(),
      String::length,
      (u, v) -> u,
      LinkedHashMap::new
    ));

    Object[] keySet = linkedHashMap.keySet().toArray();

    assertEquals("[A, BB, CCC]", Arrays.toString(keySet));
  }

}
