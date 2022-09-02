package main.java.com.baeldung.shallowdeepcoy;

public class Order {

    private String item;
    private int price;
    private Voucher voucher;

    public Order(String item, int price, Voucher voucher){
        this.item = item;
        this.price = price;
        this.voucher = voucher;
    }

    public Order(Order original, COPY_TYPE copyType) {
        switch (copyType) {
            case SHALLOW:
                this.item = original.item; // immutable object
                this.price = original.price; // primitive type
                this.voucher = original.voucher; // using the same reference for nested object
                break;
            case DEEP:
                this.item = original.item;
                this.price = original.price;
                this.voucher = new Voucher(original.voucher.getCode(), original.voucher.getDiscountPercent());
                break;

        }
    }

    public int getPrice() {
        return price;
    }

    public String getItem() {
        return item;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public enum COPY_TYPE {
        SHALLOW, DEEP
    }
}