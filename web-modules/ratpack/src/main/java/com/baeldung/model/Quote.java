package com.baeldung.model;

import java.time.Instant;

public class Quote {
    
    private Instant ts;
    private String symbol;
    private double value;
    
    public Quote() {}
    
    
    public Quote(Instant ts, String symbol, double value) {
        this.ts = ts;
        this.symbol = symbol;
        this.value = value;
    }


    /**
     * @return the ts
     */
    public Instant getTs() {
        return ts;
    }
    
    /**
     * @param ts the ts to set
     */
    public void setTs(Instant ts) {
        this.ts = ts;
    }
    
    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }
    
    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        result = prime * result + ((ts == null) ? 0 : ts.hashCode());
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        Quote other = (Quote) obj;
        if (symbol == null) {
            if (other.symbol != null)
                return false;
        } else if (!symbol.equals(other.symbol))
            return false;
        if (ts == null) {
            if (other.ts != null)
                return false;
        } else if (!ts.equals(other.ts))
            return false;
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Quote [ts=" + ts + ", symbol=" + symbol + ", value=" + value + "]";
    }
    
    
}
