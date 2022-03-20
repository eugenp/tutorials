package hexagonal.architecture.domain;

public interface SimCardService {
    void validatePin(String iccid, String pin);

}
