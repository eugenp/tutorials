package com.baeldung.spliteratorAPI;

public class RelatedAuthorCounter {
    private final int counter;
    private final boolean isRelated;

    public RelatedAuthorCounter(int counter, boolean isRelated) {
        this.counter = counter;
        this.isRelated = isRelated;
    }

    public RelatedAuthorCounter accumulate(Author author) {
        if (author.getRelatedArticleId() == 0) {
            return isRelated ? this : new RelatedAuthorCounter(counter, true);
        } else {
            return isRelated ? new RelatedAuthorCounter(counter + 1, false) : this;
        }
    }

    public RelatedAuthorCounter combine(RelatedAuthorCounter RelatedAuthorCounter) {
        return new RelatedAuthorCounter(counter + RelatedAuthorCounter.counter, RelatedAuthorCounter.isRelated);
    }

    public int getCounter() {
        return counter;
    }
}
