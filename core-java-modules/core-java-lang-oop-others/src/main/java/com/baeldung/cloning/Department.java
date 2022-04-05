package com.baeldung.cloning;

class Department implements Cloneable {
    private int deptId;
    private String deptName;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // standard getters/setters/constructors/toString
    public Department() {
    }

    public Department(int deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
