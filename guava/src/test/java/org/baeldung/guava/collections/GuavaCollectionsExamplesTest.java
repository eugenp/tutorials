package org.baeldung.guava.collections;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class GuavaCollectionsExamplesTest {

    // tests

    @Test
    public final void whenDowncastingGenerifiedCollectionToNewGenerifiedCollection_thenCastIsOK() {
        final class CastFunction<F, T extends F> implements Function<F, T> {
            @SuppressWarnings("unchecked")
            @Override
            public final T apply(final F from) {
                return (T) from;
            }
        }

        final List<Number> originalList = Lists.newArrayList();
        final List<Integer> selectedProducts = Lists.transform(originalList, new CastFunction<Number, Integer>());
        System.out.println(selectedProducts);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public final void whenDowncastingGenerifiedCollectionToNewGenerifiedCollection_thenCastIsOK2() {
        final List<Number> originalList = Lists.newArrayList();
        final List<Integer> selectedProducts = (List<Integer>) (List<? extends Number>) originalList;
        System.out.println(selectedProducts);
    }

    @Test
    public final void when_then() {
        //
    }

}
