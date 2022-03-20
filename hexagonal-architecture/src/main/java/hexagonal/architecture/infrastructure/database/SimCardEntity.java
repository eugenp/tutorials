package hexagonal.architecture.infrastructure.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sim_card")
public class SimCardEntity {

    @Id
    private String iccid;

    private String msisdn;

    private String pin;

    private String puk;

    private int invalidPinCounter;

    private String status;

    //getters and setters

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
}