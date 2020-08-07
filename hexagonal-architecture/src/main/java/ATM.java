public class ATM implements Withdrawer {
    @Override
    public void withdraw(BankAccount bankAccount, double amount) {
        // Withdraw logic of a cash withdrawal from an ATM
        bankAccount.changeBalance(-amount);
    }
}