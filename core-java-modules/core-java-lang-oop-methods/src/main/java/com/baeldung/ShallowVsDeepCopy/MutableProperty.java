package shallowVsDeepCopy;

public class MutableProperty {
    private String value;

    public MutableProperty(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;  
    }
}
