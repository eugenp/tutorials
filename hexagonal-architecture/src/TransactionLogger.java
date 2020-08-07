import java.util.UUID;

public interface TransactionLogger {
    void logDeposit(UUID userUUID, double amount);
    void logWithdrawal(UUID userUUID, double amount);
}
