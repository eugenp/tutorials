package com.baeldung.java.concept;

public class CpuSpecification implements Cloneable{
    private String cpu;
    private String frequency;

    public CpuSpecification(String cpu, String frequency) {
        this.cpu = cpu;
        this.frequency = frequency;
    }

    public String getCpu() {
        return this.cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Override
    public CpuSpecification clone() throws CloneNotSupportedException {
        return (CpuSpecification)super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        CpuSpecification castedObj = (CpuSpecification)obj;
        return cpu.equals(castedObj.getCpu());
    }

    @Override
    public String toString() {
        return cpu;
    }
}
