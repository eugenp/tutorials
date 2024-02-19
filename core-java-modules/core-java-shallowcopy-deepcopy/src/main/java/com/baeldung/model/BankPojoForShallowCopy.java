package com.baeldung.model;

public class BankPojoForShallowCopy implements Cloneable {

    private String name;
    private int code;
    private BranchPojoForShallowCopy branch;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BranchPojoForShallowCopy getBranch() {
        return branch;
    }

    public void setBranch(BranchPojoForShallowCopy branch) {
        this.branch = branch;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
