package com.baeldung.model;

public class BankPojoForDeepCopy implements Cloneable {

    private String name;
    private int code;
    private BranchPojoForDeepCopy branch;

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

    public BranchPojoForDeepCopy getBranch() {
        return branch;
    }

    public void setBranch(BranchPojoForDeepCopy branch) {
        this.branch = branch;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        BankPojoForDeepCopy clonedBankObject = (BankPojoForDeepCopy) super.clone();
        clonedBankObject.setBranch((BranchPojoForDeepCopy) clonedBankObject.getBranch()
            .clone());
        return clonedBankObject;
    }
}
