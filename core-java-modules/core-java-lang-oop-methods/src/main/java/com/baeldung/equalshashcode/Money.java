package com.baeldung.equalshashcode;

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
        boolean currencyCodeEquals = (this.currencyCode == null && other.currencyCode == null)
          || (this.currencyCode != null && this.currencyCode.equals(other.currencyCode));
        return this.amount == other.amount
          && currencyCodeEquals;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + amount;
        if (currencyCode != null) {
            result = 31 * result + currencyCode.hashCode();
        }
        return result;
    }

}
