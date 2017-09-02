package com.baeldung.commons.lang3;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BuilderMethods {

    private final int intValue;
    private final String strSample;

    public BuilderMethods(final int newId, final String newName) {
        this.intValue = newId;
        this.strSample = newName;
    }

    public int getId() {
        return this.intValue;
    }

    public String getName() {
        return this.strSample;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.intValue)
            .append(this.strSample)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BuilderMethods == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final BuilderMethods otherObject = (BuilderMethods) obj;

        return new EqualsBuilder().append(this.intValue, otherObject.intValue)
            .append(this.strSample, otherObject.strSample)
            .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("INTVALUE", this.intValue)
            .append("STRINGVALUE", this.strSample)
            .toString();
    }

    public static void main(final String[] arguments) {
        final BuilderMethods simple1 = new BuilderMethods(1, "The First One");
        System.out.println(simple1.getName());
        System.out.println(simple1.hashCode());
        System.out.println(simple1.toString());
    }
}
