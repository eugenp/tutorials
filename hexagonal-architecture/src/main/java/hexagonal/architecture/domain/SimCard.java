package hexagonal.architecture.domain;

public class SimCard {

    private String iccid;

    private String msisdn;

    private String pin;

    private String puk;

    private int invalidPinCounter;

    private String status;

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPuk() {
        return puk;
    }

    public void setPuk(String puk) {
        this.puk = puk;
    }

    public int getInvalidPinCounter() {
        return invalidPinCounter;
    }

    public void setInvalidPinCounter(int invalidPinCounter) {
        this.invalidPinCounter = invalidPinCounter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public enum Status {
        ACTIVE, BLOCKED;
    }
}
