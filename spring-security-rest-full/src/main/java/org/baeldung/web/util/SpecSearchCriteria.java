package org.baeldung.web.util;

public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;

    public SpecSearchCriteria() {

    }

    public SpecSearchCriteria(final String key, final SearchOperation operation, final Object value) {
        super();
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

}
