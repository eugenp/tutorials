import java.util.UUID;

public class Bank implements TransactionLogger {
    @Override
    public void logDeposit(UUID userUUID, double amount) {
        // Log transaction in the bank for a cash deposit to a user account
    }

    @Override
    public void logWithdrawal(UUID userUUID, double amount) {
        // Log transaction in the bank for a cash withdrawal from a user account
    }
}
