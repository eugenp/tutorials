package com.baeldung.collections;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class ArrayListUnitTest {

    private List<String> stringsToSearch;

    @Before
    public void setUp() {
        List<String> list = LongStream.range(0, 16).boxed().map(Long::toHexString).collect(toCollection(ArrayList::new));
        stringsToSearch = new ArrayList<>(list);
        stringsToSearch.addAll(list);
    }

    @Test
    public void givenNewArrayList_whenCheckCapacity_thenDefaultValue() {
        List<String> list = new ArrayList<>();
        assertTrue(list.isEmpty());
    }

    @Test
    public void givenCollection_whenProvideItToArrayListCtor_thenArrayListIsPopulatedWithItsElements() {
        Collection<Integer> numbers = IntStream.range(0, 10).boxed().collect(toSet());

        List<Integer> list = new ArrayList<>(numbers);
        assertEquals(10, list.size());
        assertTrue(numbers.containsAll(list));
    }

    @Test
    public void givenElement_whenAddToArrayList_thenIsAdded() {
        List<Long> list = new ArrayList<>();

        list.add(1L);
        list.add(2L);
        list.add(1, 3L);

        assertThat(Arrays.asList(1L, 3L, 2L), equalTo(list));
    }

    @Test
    public void givenCollection_whenAddToArrayList_thenIsAdded() {
        List<Long> list = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        LongStream.range(4, 10).boxed().collect(collectingAndThen(toCollection(ArrayList::new), ys -> list.addAll(0, ys)));

        assertThat(Arrays.asList(4L, 5L, 6L, 7L, 8L, 9L, 1L, 2L, 3L), equalTo(list));
    }

    @Test
    public void givenExistingElement_whenCallIndexOf_thenReturnCorrectIndex() {
        assertEquals(10, stringsToSearch.indexOf("a"));
        assertEquals(26, stringsToSearch.lastIndexOf("a"));
    }

    @Test
    public void givenCondition_whenIterateArrayList_thenFindAllElementsSatisfyingCondition() {
        Iterator<String> it = stringsToSearch.iterator();
        Set<String> matchingStrings = new HashSet<>(Arrays.asList("a", "c", "9"));

        List<String> result = new ArrayList<>();
        while (it.hasNext()) {
            String s = it.next();
            if (matchingStrings.contains(s)) {
                result.add(s);
            }
        }

        assertEquals(6, result.size());
    }

    @Test
    public void givenPredicate_whenIterateArrayList_thenFindAllElementsSatisfyingPredicate() {
        Set<String> matchingStrings = new HashSet<>(Arrays.asList("a", "c", "9"));

        List<String> result = stringsToSearch.stream().filter(matchingStrings::contains).collect(toCollection(ArrayList::new));

        assertEquals(6, result.size());
    }

    @Test
    public void givenSortedArray_whenUseBinarySearch_thenFindElement() {
        List<String> copy = new ArrayList<>(stringsToSearch);
        Collections.sort(copy);
        int index = Collections.binarySearch(copy, "f");
        assertThat(index, not(equalTo(-1)));
    }

    @Test
    public void givenIndex_whenRemove_thenCorrectElementRemoved() {
        List<Integer> list = IntStream.range(0, 10).boxed().collect(toCollection(ArrayList::new));
        Collections.reverse(list);

        list.remove(0);
        assertThat(list.get(0), equalTo(8));

        list.remove(Integer.valueOf(0));
        assertFalse(list.contains(0));
    }

    @Test
    public void givenListIterator_whenReverseTraversal_thenRetrieveElementsInOppositeOrder() {
        List<Integer> list = IntStream.range(0, 10).boxed().collect(toCollection(ArrayList::new));
        ListIterator<Integer> it = list.listIterator(list.size());
        List<Integer> result = new ArrayList<>(list.size());
        while (it.hasPrevious()) {
            result.add(it.previous());
        }

        Collections.reverse(list);
        assertThat(result, equalTo(list));
    }

    @Test
    public void givenCondition_whenIterateArrayList_thenRemoveAllElementsSatisfyingCondition() {
        Set<String> matchingStrings = Sets.newHashSet("a", "b", "c", "d", "e", "f");

        Iterator<String> it = stringsToSearch.iterator();
        while (it.hasNext()) {
            if (matchingStrings.contains(it.next())) {
                it.remove();
            }
        }

        assertEquals(20, stringsToSearch.size());
    }
}
