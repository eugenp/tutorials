package com.baeldung.deep_shallow_copy_2;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Example implements Cloneable {

    private String value;

    private Date creationDate;

    private List<String> typeList;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Example that = (Example) o;
        return Objects.equals(that.getValue(), this.value) &&
                Objects.equals(that.getCreationDate(), this.creationDate) &&
                Objects.equals(that.getTypeList(), this.typeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value, creationDate, typeList);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}