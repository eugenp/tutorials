import java.util.UUID;

public interface Withdrawer {
    void withdraw(UUID userUUID, double amount);
}
