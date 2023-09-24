package com.baeldung.spliterator;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class RelatedAuthorSpliterator implements Spliterator<Author> {
    private final List<Author> authors;
    private int currentIndex;
    private final int relatedArticleId;

    public RelatedAuthorSpliterator(List<Author> authors, int relatedArticleId) {
        this.authors = authors;
        this.currentIndex = 0;
        this.relatedArticleId = relatedArticleId;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Author> action) {
        while (currentIndex < authors.size()) {
            Author author = authors.get(currentIndex);
            currentIndex++;
            if (author.getRelatedArticleId() == relatedArticleId) {
                action.accept(author);
                return true;
            }
        }
        return false;
    }

    @Override
    public Spliterator<Author> trySplit() {
        int currentSize = authors.size() - currentIndex;
        if (currentSize < 2) {
            return null;
        }

        int splitIndex = currentIndex + currentSize / 2;
        RelatedAuthorSpliterator newSpliterator = new RelatedAuthorSpliterator(authors.subList(currentIndex, splitIndex), relatedArticleId);
        currentIndex = splitIndex;
        return newSpliterator;
    }

    @Override
    public long estimateSize() {
        return authors.size() - currentIndex;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED | NONNULL;
    }
}
