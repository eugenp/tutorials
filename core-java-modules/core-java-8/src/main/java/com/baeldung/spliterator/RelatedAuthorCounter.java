package com.baeldung.spliterator;

public class RelatedAuthorCounter {
    private final int counter;
    private final int relatedArticleId;

    public RelatedAuthorCounter(int counter, int relatedArticleId) {
        this.counter = counter;
        this.relatedArticleId = relatedArticleId;
    }

    public RelatedAuthorCounter accumulate(Author author) {
        return new RelatedAuthorCounter(counter + 1, author.getRelatedArticleId());
    }

    public RelatedAuthorCounter combine(RelatedAuthorCounter relatedAuthorCounter) {
        return new RelatedAuthorCounter(counter + relatedAuthorCounter.counter, relatedAuthorCounter.relatedArticleId);
    }

    public int getCounter() {
        return counter;
    }
}
