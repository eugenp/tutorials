package com.baeldung.commons.chain;

import org.apache.commons.chain.impl.ContextBase;

public class AtmRequestContext extends ContextBase {
    private int totalInAtm;
    private int remaining;
    private int hundred;
    private int fifty;
    private int ten;

    public int getTotalInAtm() {
        return totalInAtm;
    }

    public void setTotalInAtm(int totalInAtm) {
        this.totalInAtm = totalInAtm;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getHundred() {
        return hundred;
    }

    public void setHundred(int hundred) {
        this.hundred = hundred;
    }

    public int getFifty() {
        return fifty;
    }

    public void setFifty(int fifty) {
        this.fifty = fifty;
    }

    public int getTen() {
        return ten;
    }

    public void setTen(int ten) {
        this.ten = ten;
    }
}
