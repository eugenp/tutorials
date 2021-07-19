package com.baeldung.equalshashcode.entities;

public class PrimitiveClass {

    private boolean primitiveBoolean;
    private int primitiveInt;

    public PrimitiveClass(boolean primitiveBoolean, int primitiveInt) {
        super();
        this.primitiveBoolean = primitiveBoolean;
        this.primitiveInt = primitiveInt;
    }

    protected boolean isPrimitiveBoolean() {
        return primitiveBoolean;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (primitiveBoolean ? 1231 : 1237);
        result = prime * result + primitiveInt;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PrimitiveClass other = (PrimitiveClass) obj;
        if (primitiveBoolean != other.primitiveBoolean)
            return false;
        if (primitiveInt != other.primitiveInt)
            return false;
        return true;
    }

    protected void setPrimitiveBoolean(boolean primitiveBoolean) {
        this.primitiveBoolean = primitiveBoolean;
    }

    protected int getPrimitiveInt() {
        return primitiveInt;
    }

    protected void setPrimitiveInt(int primitiveInt) {
        this.primitiveInt = primitiveInt;
    }
}
