import java.util.UUID;

public interface Depositor {
    void deposit(UUID userUUID,  double amount);
}
