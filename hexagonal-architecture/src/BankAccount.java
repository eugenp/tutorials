import java.util.UUID;

public class BankAccount {
    private Depositor depositor;
    private Withdrawer withdrawer;
    private TransactionLogger transactionLogger;
    private UUID userUUID;
    private double balance;
    private double depositCap = 1000000;
    private double withdrawalCap = 1000000;

    public BankAccount(Depositor depositor, Withdrawer withdrawer, UUID userUUID, double balance) {
        this.depositor = depositor;
        this.withdrawer = withdrawer;
        this.userUUID = userUUID;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (isValidDepositAmount(amount)) {
            depositor.deposit(userUUID, amount);
            transactionLogger.logDeposit(userUUID, amount);
        }
    }

    public void withdraw(double amount) {
        if (isValidWithdrawalAmount(amount)) {
            withdrawer.withdraw(userUUID, amount);
            transactionLogger.logWithdrawal(userUUID, amount);
        }
    }

    private boolean isValidDepositAmount(double amount) {
        return (amount >= 0 && amount <= depositCap);
    }

    private boolean isValidWithdrawalAmount(double amount) {
        return (amount <= balance && amount <= withdrawalCap);
    }
}
