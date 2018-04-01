package datajpa.repository;


import datajpa.domain.domain6.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {

    @Query(value = "select BD.BILLING_DETIALS_ID from BillingDetails BD left outer join CREDIT_CARD CC on BD.BILLING_DETAILS_ID = CC.CREDIT_CARD_ID")
    List<BillingDetails> findBillingDetails();
}
