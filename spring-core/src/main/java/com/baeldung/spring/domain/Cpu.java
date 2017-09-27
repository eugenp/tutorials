package com.baeldung.spring.domain;

public class Cpu {

    private String processor;

    private String ram;
    
    private String hd;
    
    public Cpu(String processor, String ram, String hd) {
        this.processor = processor;
        this.ram = ram;
        this.hd = hd;
    }
    
    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }



}
