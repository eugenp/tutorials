package com.baeldung.paymentservice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.smartcardio.CardException;
import java.util.UUID;


@RestController
public class PaymentService {


    @PostMapping("/pay/{orderNum}")
    public PaymentResponse createPayment(@PathVariable String orderNum, @RequestBody PaymentDTO paymentDTO) {

            Payment payment = new Payment();
            payment.setPaymentId(UUID.randomUUID().toString().replace("-", ""));
            String firstName = paymentDTO.getFirstName();
            String lastName = paymentDTO.getLastName();
            payment.setCustomerFullName(firstName + " " + lastName);
            String cardNumber = paymentDTO.getCardNumber();

            if(CardValidator.validate(cardNumber)){
                payment.setPaymentMethod("CREDITCARD");
            } else try {
                throw new CardException("Card with number:"+ cardNumber + " is invalid");
            } catch (CardException e) {
                e.printStackTrace();
            }
            payment.setAmount(paymentDTO.getAmount());
            payment.setCurrency(paymentDTO.getCurrency());

            return new PaymentResponse(payment.getPaymentId(), payment.getPaymentMethod(), payment.getCustomerFullName(), payment.getAmount(), payment.getCurrency());

    }
}
