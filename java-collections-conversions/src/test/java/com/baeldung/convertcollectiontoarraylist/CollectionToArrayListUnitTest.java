package com.baeldung.convertcollectiontoarraylist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import static java.util.stream.Collectors.toCollection;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chris
 */
public class CollectionToArrayListUnitTest {
    private static Collection<Foo> srcCollection = new HashSet<>();

    public CollectionToArrayListUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        int i = 0;
        Foo john = new Foo(i++, "John", null);
        Foo mary = new Foo(i++, "Mary", null);
        Foo sam = new Foo(i++, "Sam", john);
        Foo alice = new Foo(i++, "Alice", john);
        Foo buffy = new Foo(i++, "Buffy", sam);
        srcCollection.add(john);
        srcCollection.add(mary);
        srcCollection.add(sam);
        srcCollection.add(alice);
        srcCollection.add(buffy);
    }
    
    /**
     * Section 3. Using the ArrayList Constructor
     */
    @Test
    public void whenUsingConstructor_thenVerifyShallowCopy() {
        ArrayList<Foo> newList = new ArrayList<>(srcCollection);
        verifyShallowCopy(srcCollection, newList);
    }
    
    /**
     * Section 4. Using the Streams API
     */
    @Test
    public void whenUsingStream_thenVerifyShallowCopy() {
        ArrayList<Foo> newList = srcCollection.stream().collect(toCollection(ArrayList::new));

        verifyShallowCopy(srcCollection, newList);
    }
    
    /**
     * Section 5. Deep Copy
     */
    @Test
    public void whenUsingDeepCopy_thenVerifyDeepCopy() {
        ArrayList<Foo> newList = srcCollection.stream()
          .map(foo -> foo.deepCopy())
          .collect(toCollection(ArrayList::new));

        verifyDeepCopy(srcCollection, newList);
    }
    
    /**
     * Section 6. Controlling the List Order
     */
    @Test
    public void whenUsingSortedStream_thenVerifySortOrder() {
        ArrayList<Foo> newList = srcCollection.stream()
          .sorted(Comparator.comparing(Foo::getName))
          .collect(toCollection(ArrayList::new));

        assertTrue("ArrayList is not sorted by name", isSorted(newList));
    }
    
    /**
     * Verify that the contents of the two collections are the same
     * @param a
     * @param b 
     */
    private void verifyShallowCopy(Collection a, Collection b) {
        assertEquals("Collections have different lengths", a.size(), b.size());
        Iterator<Foo> iterA = a.iterator();
        Iterator<Foo> iterB = b.iterator();
        while (iterA.hasNext()) {
            // use '==' to test instance identity
            assertTrue("Foo instances differ!", iterA.next() == iterB.next());
        }
    }
    
    /**
     * Verify that the contents of the two collections are the same
     * @param a
     * @param b 
     */
    private void verifyDeepCopy(Collection a, Collection b) {
        assertEquals("Collections have different lengths", a.size(), b.size());
        Iterator<Foo> iterA = a.iterator();
        Iterator<Foo> iterB = b.iterator();
        while (iterA.hasNext()) {
            Foo nextA = iterA.next();
            Foo nextB = iterB.next();
            // should not be same instance
            assertFalse("Foo instances are the same!", nextA == nextB);
            // but should have same content
            assertFalse("Foo instances have different content!", fooDiff(nextA, nextB));
        }
    }

    /**
     * Return true if the contents of a and b differ.  Test parent recursively
     * @param a
     * @param b
     * @return False if the two items are the same
     */
    private boolean fooDiff(Foo a, Foo b) {
        if (a != null && b != null) {
            return a.getId() != b.getId() 
              || !a.getName().equals(b.getName()) 
              || fooDiff(a.getParent(), b.getParent());
        }
        return !(a == null && b == null);
    }
    
    /**
     * @param c collection of Foo
     * @return true if the collection is sorted by name
     */
    private static boolean isSorted(Collection<Foo> c) {
        String prevName = null;
        for (Foo foo : c) {
            if (prevName == null || foo.getName().compareTo(prevName) > 0) {
                prevName = foo.getName();
            } else {
                return false;
            }
        }
        return true;
    }
}
