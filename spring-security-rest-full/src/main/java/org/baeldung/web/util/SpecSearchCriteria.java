package org.baeldung.web.util;

public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean lowPrecedence;

    public SpecSearchCriteria() {

    }

    public SpecSearchCriteria(final String key, final SearchOperation operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SpecSearchCriteria(final String lowPrecedenceIndicator, final String key, final SearchOperation operation, final Object value) {
        super();
        this.lowPrecedence = lowPrecedenceIndicator != null && lowPrecedenceIndicator.equals(SearchOperation.LOW_PRECEDENCE_INDICATOR);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(final SearchOperation operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public boolean isLowPrecedence() {
        return lowPrecedence;
    }

    public void setLowPrecedence(boolean lowPrecedence) {
        this.lowPrecedence = lowPrecedence;
    }

}
