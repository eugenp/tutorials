package com.baeldung.lombok.tostring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToStringUnitTest {

    @Test
    void whenPrintObject_thenOutputIsCorrect() {
        Account account = new Account();
        account.setId("12345");
        account.setName("An account");
        account.setAccountNumber("11111");      // should not be present in output
        account.set$ignored("ignored value");   // should not be present in output

        assertThat(account.toString())
          .isEqualTo("Account(id=12345, name=An account, description=Account description)");
    }

    @Test
    void whenPrintSubclassWithSuper_thenOutputIsCorrect() {
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setSavingAccountId("5678");
        savingAccount.setId("12345");
        savingAccount.setName("An account");

        assertThat(savingAccount.toString())
          .isEqualTo("SavingAccount(super=Account(id=12345, name=An account, description=Account description), savingAccountId=5678)");
    }

@Test
void whenPrintArrays_thenOutputIsCorrect() {
    RewardAccount account = new RewardAccount();
    account.setRewardAccountId("12345");

    // circular ref, just for demonstration
    Object[] relatedAccounts = new Object[2];
    relatedAccounts[0] = "54321";
    relatedAccounts[1] = relatedAccounts;

    account.setRelatedAccounts(relatedAccounts);

    assertThat(account.toString())
      .isEqualTo("RewardAccount(rewardAccountId=12345, relatedAccounts=[54321, [...]])");
}

    @Test
    void whenPrintSubclassWithoutSuper_thenOutputIsCorrect() {
        RewardAccount rewardAccount = new RewardAccount();
        rewardAccount.setRewardAccountId("12345");

        assertThat(rewardAccount.toString())
          .isEqualTo("RewardAccount(rewardAccountId=12345, relatedAccounts=null)");
    }

    @Test
    void whenPrintEnum_thenOutputIsCorrect() {
        assertThat(AccountType.CHECKING.toString())
          .isEqualTo("AccountType.CHECKING");

        assertThat(AccountType.SAVING.toString())
          .isEqualTo("AccountType.SAVING");
    }

}
