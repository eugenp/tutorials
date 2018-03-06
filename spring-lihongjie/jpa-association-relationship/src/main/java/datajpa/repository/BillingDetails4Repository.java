package datajpa.repository;


import datajpa.domain.domain6.BankAccount4;
import datajpa.domain.domain6.BillingDetails4;
import datajpa.domain.domain6.CreditCard4;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillingDetails4Repository extends JpaRepository<BillingDetails4, Long> {


//    @Query("select BD.owner,BD.BILLING_DETAILS_ID,CC.CC_NUMBER From CreditCard4 CC inner join CC.billingDetails4 BD where BD.BILLING_DETAILS_ID = CC.CREDIT_CARD_ID")
//    List<CreditCard4> findCreditCards();

    @Query("select BD.owner From BillingDetails4 BD  inner join BD.bankAccount4 BA ON BD.BILLING_DETAILS_ID = BA.BILLING_DETAILS_ID")
    List<BankAccount4> findBankAccounts();
}
