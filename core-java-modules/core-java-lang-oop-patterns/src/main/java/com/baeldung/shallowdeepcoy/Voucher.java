package main.java.com.baeldung.shallowdeepcoy;

public class Voucher {

    private String code;
    private double discountPercent;

    public Voucher(String code, double discountPercent){
        // only immutable / primitive objects
        this.code = code;
        this.discountPercent = discountPercent;
    }

    public String getCode() {
        return code;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
}
