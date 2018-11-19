package com.baeldung.map.hashcode;

class WrongVoucher extends Money {

    private String store;

    WrongVoucher(int amount, String currencyCode, String store) {
        super(amount, currencyCode);

        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WrongVoucher))
            return false;
        WrongVoucher other = (WrongVoucher)o;
        return this.amount == other.amount
          && this.currencyCode == other.currencyCode
          && this.store == other.store;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + amount;
        result = 31 * result + currencyCode.hashCode();
        result = 31 * result + store.hashCode();
        return result;
    }
}