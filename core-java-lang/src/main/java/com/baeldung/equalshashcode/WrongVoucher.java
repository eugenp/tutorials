package com.baeldung.equalshashcode;

/* (non-Javadoc)
* This class extends the Money class that has overridden the equals method and once again overrides the equals method.
*
* To see which problems this leads to:
* MoneyUnitTest.givenMoneyAndVoucherInstances_whenEquals_thenReturnValuesArentSymmetric
*/
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
        boolean currencyCodeEquals = (this.currencyCode == null && other.currencyCode == null)
          || (this.currencyCode != null && this.currencyCode.equals(other.currencyCode));
        boolean storeEquals = (this.store == null && other.store == null)
          || (this.store != null && this.store.equals(other.store));
        return this.amount == other.amount
          && currencyCodeEquals
          && storeEquals;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + amount;
        if (this.currencyCode != null) {
            result = 31 * result + currencyCode.hashCode();
        }
        if (this.store != null) {
            result = 31 * result + store.hashCode();
        }
        return result;
    }
}