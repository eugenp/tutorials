package datajpa.repository;


import datajpa.domain.domain6.BankAccount3;
import datajpa.domain.domain6.BillingDetails3;
import datajpa.domain.domain6.CreditCard3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillingDetails3Repository extends JpaRepository<BillingDetails3, Long> {


    @Query("From BillingDetails3 WHERE BILLING_DETAILS_TYPE = 'CC'")
    List<CreditCard3> findCreditCards();

    @Query("From BillingDetails3 WHERE BILLING_DETAILS_TYPE = 'BA'")
    List<BankAccount3> findBankAccounts();
}
