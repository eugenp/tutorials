package org.baeldung.java.collections;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class ArrayListTest {

    private List<String> stringsToSearch;

    @Before
    public void setUp() {
        List<String> xs = LongStream.range(0, 16)
            .boxed()
            .map(Long::toHexString)
            .collect(toList());
        stringsToSearch = new ArrayList<>(xs);
        stringsToSearch.addAll(xs);
    }

    @Test
    public void givenNewArrayList_whenCheckCapacity_thenDefaultValue() {
        List<String> xs = new ArrayList<>();
        assertTrue(xs.isEmpty());
    }

    @Test
    public void givenCollection_whenProvideItToArrayListCtor_thenArrayListIsPopulatedWithItsElements() {
        Collection<Integer> numbers =
            IntStream.range(0, 10).boxed().collect(toSet());

        List<Integer> xs = new ArrayList<>(numbers);
        assertEquals(10, xs.size());
        assertTrue(numbers.containsAll(xs));
    }

    @Test
    public void givenElement_whenAddToArrayList_thenIsAdded() {
        List<Long> xs = new ArrayList<>();

        xs.add(1L);
        xs.add(2L);
        xs.add(1, 3L);

        assertThat(Arrays.asList(1L, 3L, 2L), equalTo(xs));
    }

    @Test
    public void givenCollection_whenAddToArrayList_thenIsAdded() {
        List<Long> xs = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        LongStream.range(4, 10).boxed()
            .collect(collectingAndThen(toList(), ys -> xs.addAll(0, ys)));

        assertThat(Arrays.asList(4L, 5L, 6L, 7L, 8L, 9L, 1L, 2L, 3L), equalTo(xs));
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

        List<String> result = stringsToSearch
            .stream()
            .filter(matchingStrings::contains)
            .collect(toList());

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
        List<Integer> xs = IntStream.range(0, 10).boxed().collect(toList());
        Collections.reverse(xs);

        xs.remove(0);
        assertThat(xs.get(0), equalTo(8));

        xs.remove(Integer.valueOf(0));
        assertFalse(xs.contains(0));
    }

    @Test
    public void givenListIterator_whenReverseTraversal_thenRetrieveElementsInOppositeOrder() {
        List<Integer> xs = IntStream.range(0, 10).boxed().collect(toList());
        ListIterator<Integer> it = xs.listIterator(xs.size());
        List<Integer> result = new ArrayList<>(xs.size());
        while (it.hasPrevious()) {
            result.add(it.previous());
        }

        Collections.reverse(xs);
        assertThat(result, equalTo(xs));
    }

    @Test
    public void givenCondition_whenIterateArrayList_thenRemoveAllElementsSatisfyingCondition() {
        Set<String> matchingStrings
            = Sets.newHashSet("a", "b", "c", "d", "e", "f");

        Iterator<String> it = stringsToSearch.iterator();
        while (it.hasNext()) {
            if (matchingStrings.contains(it.next())) {
                it.remove();
            }
        }

        assertEquals(20, stringsToSearch.size());
    }
}
