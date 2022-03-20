package hexagonal.architecture.domain;

public interface SimCards {
    SimCard find(String iccid);

    void update(SimCard s);

    boolean isBlocked(String iccid);

}
