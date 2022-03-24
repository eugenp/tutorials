package main.java.com.baeldung.pattern.hexagonal.eval.domain.model;

public class ContactNumber {
    private String internationalCode;
    private String number;

    public ContactNumber(String internationalCode, String number) {
        this.internationalCode = internationalCode;
        this.number = number;
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
