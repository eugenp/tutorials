package com.baeldung.equalshashcode;

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
        boolean valueEquals = (this.value == null && other.value == null)
          || (this.value != null && this.value.equals(other.value));
        boolean storeEquals = (this.store == null && other.store == null)
          || (this.store != null && this.store.equals(other.store));
        return valueEquals && storeEquals;
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.value != null) {
            result = 31 * result + value.hashCode();
        }
        if (this.store != null) {
            result = 31 * result + store.hashCode();
        }
        return result;
    }
}