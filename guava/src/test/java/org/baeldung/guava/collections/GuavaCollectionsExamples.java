package org.baeldung.guava.collections;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class GuavaCollectionsExamples {

    @SuppressWarnings({ "unused", "unchecked" })
    @Test
    public final void whenCastingAllElementsOfACollectionToSubtype_thenCastIsOK() {
        final class CastFunction<F, T extends F> implements Function<F, T> {
            @Override
            @SuppressWarnings("unchecked")
            public final T apply(final F from) {
                return (T) from;
            }
        }

        final List<Number> originalList = Lists.newArrayList();
        final List<Integer> selectedProductsQuick = (List<Integer>) (List<? extends Number>) originalList;
        final List<Integer> selectedProducts = Lists.transform(originalList, new CastFunction<Number, Integer>());
        System.out.println(selectedProducts);
    }

    @Test
    public final void when_then() {

    }

}
