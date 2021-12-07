package com.baeldung.patterns.hexagonal.architecture.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String ticker;

    Stock() {
    }

    public Stock(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());
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
        Stock other = (Stock) obj;
        if (ticker == null) {
            if (other.ticker != null)
                return false;
        } else if (!ticker.equals(other.ticker))
            return false;
        return true;
    }
}
