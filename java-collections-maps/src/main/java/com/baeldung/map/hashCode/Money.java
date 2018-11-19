package com.baeldung.map.hashcode;

class Money {

    int amount;
    String currencyCode;

    Money(int amount, String currencyCode) {
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Money))
            return false;
        Money other = (Money)o;
        return this.amount == other.amount
          && this.currencyCode == other.currencyCode;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + amount;
        result = 31 * result + currencyCode.hashCode();
        return result;
    }

}
