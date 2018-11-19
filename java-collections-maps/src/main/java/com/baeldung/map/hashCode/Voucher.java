package com.baeldung.map.hashcode;

class Voucher {

    private Money value;
    private String store;

    Voucher(int amount, String currencyCode, String store) {
        this.value = new Money(amount, currencyCode);
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Voucher))
            return false;
        Voucher other = (Voucher)o;
        return this.value == other.value
          && this.store == other.store;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + value.hashCode();
        result = 31 * result + store.hashCode();
        return result;
    }
}