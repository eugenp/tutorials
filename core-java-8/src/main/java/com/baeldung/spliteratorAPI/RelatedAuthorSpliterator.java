package com.baeldung.spliteratorAPI;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class RelatedAuthorSpliterator implements Spliterator<Author> {
    private final List<Author> list;
    private int current = 0;

    public RelatedAuthorSpliterator(List<Author> list) {
        this.list = list;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Author> action) {
        action.accept(list.get(current++));
        return current < list.size();
    }

    @Override
    public Spliterator<Author> trySplit() {
        int currentSize = list.size() - current;
        if (currentSize < 10) {
            return null;
        }
        for (int splitPos = currentSize / 2 + current; splitPos < list.size(); splitPos++) {
            if (list.get(splitPos)
                .getRelatedArticleId() == 0) {
                Spliterator<Author> spliterator = new RelatedAuthorSpliterator(list.subList(current, splitPos));
                current = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return list.size() - current;
    }

    @Override
    public int characteristics() {
        return SIZED + CONCURRENT;
    }
}
