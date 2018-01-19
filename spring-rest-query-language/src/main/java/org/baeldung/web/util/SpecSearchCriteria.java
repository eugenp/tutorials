package org.baeldung.web.util;

public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Comparable value;
    private boolean orPredicate;

    public SpecSearchCriteria() {

    }

    public SpecSearchCriteria(final String key, final SearchOperation operation, final Comparable value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SpecSearchCriteria(final String orPredicate, final String key, final SearchOperation operation, final Comparable value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SpecSearchCriteria(String key, String operation, String prefix, String value, String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
        }
        this.key = key;
        this.operation = op;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String key() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public SpecSearchCriteria key(final String key) {
        this.key = key;
        return this;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public SearchOperation operation() {
        return operation;
    }

    public void setOperation(final SearchOperation operation) {
        this.operation = operation;
    }

    public SpecSearchCriteria operation(final SearchOperation operation) {
        this.operation = operation;
        return this;
    }

    public Comparable getValue() {
        return value;
    }

    public Comparable value() {
        return value;
    }

    public void setValue(final Comparable value) {
        this.value = value;
    }

    public SpecSearchCriteria value(final Comparable value) {
        this.value = value;
        return this;
    }

    public boolean isOrPredicate() {
        return orPredicate;
    }

    public void setOrPredicate(boolean orPredicate) {
        this.orPredicate = orPredicate;
    }

    public SpecSearchCriteria orPredicate(boolean orPredicate) {
        this.orPredicate = orPredicate;
        return this;
    }
}
