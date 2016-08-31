package org.baeldung.java.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import org.apache.commons.collections4.CollectionUtils;

public class CollectionsJoinAndSplitJUnitTest {

    private Collection<String> veggieToppings = Arrays.asList("Olive Oil",
      "Alfredo", "Mozzarella", "Olives", "Spinach");
    private Collection<String> supremeToppings = Arrays.asList("Olive Oil", "Alfredo",
      "Mozzarella", "Mozzarella", "Feta", "Olives", "Spinach", "Green Peppers", "Artichoke Hearts");
    private Collection<String> comboToppings = Arrays.asList("Olive Oil", "Alfredo", "Mozzarella",
      "Mozzarella", "Feta", "Olives", "Spinach", "Green Peppers", "Artichoke Hearts");

    private Collection<String> allVegetables = Arrays.asList("Olives", "Spinach",
      "Green Peppers", "Artichoke Hearts");

    @Test
    public void givenThreeCollections_whenJoiningWithJavaAddAll_shouldSucceed() {
        Collection<String> sauces = Arrays.asList("Olive Oil", "Alfredo");
        Collection<String> cheeses = Arrays.asList("Mozzarella");
        Collection<String> vegetables = Arrays.asList("Olives", "Spinach");

        Collection<String> veggiePizza = new ArrayList<>(sauces);
        veggiePizza.addAll(cheeses);
        veggiePizza.addAll(vegetables);

        Assert.assertArrayEquals(veggiePizza.toArray(), new String[] {"Olive Oil",
          "Alfredo", "Mozzarella", "Olives", "Spinach"});
    }

    @Test
    public void givenThreeCollections_whenJoiningWithJavaStream_shouldSucceed() {
        Collection<String> sauces = Arrays.asList("Olive Oil", "Alfredo");
        Collection<String> cheeses = Arrays.asList("Mozzarella", "Mozzarella", "Feta");
        Collection<String> vegetables = Arrays.asList("Olives", "Spinach",
          "Green Peppers", "Artichoke Hearts");
        
        Collection<String> supremePizza = Stream.of(sauces, cheeses, vegetables)
          .flatMap(Collection::stream)
          .collect(Collectors.toCollection(ArrayList::new));
          
        Assert.assertArrayEquals(supremePizza.toArray(), new String[] {"Olive Oil",
          "Alfredo", "Mozzarella", "Mozzarella", "Feta", "Olives", "Spinach",
          "Green Peppers", "Artichoke Hearts"});
    }

    @Test
    public void givenThreeCollections_whenMergingWithCommonsUnion_shouldSucceed() {
        Collection<String> comboPizza = new ArrayList<>();
        comboPizza = CollectionUtils.union(veggieToppings, supremeToppings);
        assertEquals(new TreeSet(comboPizza), new TreeSet(Arrays.asList("Olive Oil",
          "Alfredo", "Mozzarella", "Mozzarella", "Feta", "Olives", "Spinach",
          "Green Peppers", "Artichoke Hearts")));
    }

    @Test
    public void givenThreeCollections_whenMergingAsJavaTreeSets_shouldSucceed() {
        TreeSet set = new TreeSet(veggieToppings);
        set.addAll(supremeToppings);
        Collection<String> comboPizza = new ArrayList<>(set);
        assertEquals(new TreeSet(comboPizza), new TreeSet(Arrays.asList("Olive Oil",
          "Alfredo", "Mozzarella", "Mozzarella", "Feta", "Olives", "Spinach",
          "Green Peppers", "Artichoke Hearts")));
    }
    
    @Test
    public void givenOneCollection_whenSplittingWithCommonsSubtract_shouldSucceed() {
        Collection<String> cheesePizza =
          CollectionUtils.subtract(supremeToppings, allVegetables);
        Collection<String> removedToppings =
          CollectionUtils.subtract(supremeToppings, cheesePizza);
        assertEquals(new TreeSet(cheesePizza), new TreeSet(Arrays.asList("Olive Oil",
          "Alfredo", "Mozzarella", "Mozzarella", "Feta")));
        assertEquals(new TreeSet(removedToppings), new TreeSet(Arrays.asList("Olives",
          "Spinach", "Green Peppers", "Artichoke Hearts")));
    }

    @Test
    public void givenOneCollection_whenSplittingWithStreamFilter_shouldSucceed() {
        Collection<String> noSauce = veggieToppings.stream()
          .filter(veggieToppings -> !veggieToppings.contains("Alfredo"))
          .collect(Collectors.toCollection(ArrayList::new));
        assertEquals(new TreeSet(noSauce), new TreeSet(Arrays.asList("Olive Oil",
          "Mozzarella", "Olives", "Spinach")));
    }
    
    @Test
    public void givenOneCollection_whenSplittingWithSubList_shouldSucceed() {
        List lineOne = (new ArrayList(supremeToppings))
          .subList(0, supremeToppings.size()/2);
        List lineTwo = (new ArrayList(supremeToppings))
          .subList(supremeToppings.size()/2, supremeToppings.size());
        assertEquals(new TreeSet(lineOne), new TreeSet(Arrays.asList("Olive Oil",
          "Alfredo", "Mozzarella", "Mozzarella")));
        assertEquals(new TreeSet(lineTwo), new TreeSet(Arrays.asList("Feta",
          "Olives", "Spinach", "Green Peppers", "Artichoke Hearts")));
    }
}
