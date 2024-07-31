package io.orkes.example.saga.service;

import io.orkes.example.saga.dao.PaymentsDAO;
import io.orkes.example.saga.pojos.Payment;
import io.orkes.example.saga.pojos.PaymentDetails;
import io.orkes.example.saga.pojos.PaymentMethod;
import io.orkes.example.saga.pojos.PaymentRequest;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class PaymentService {

    private static final PaymentsDAO paymentsDAO = new PaymentsDAO("jdbc:sqlite:food_delivery.db");

    public static Payment createPayment(PaymentRequest paymentRequest) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        Payment payment = new Payment();
        payment.setPaymentId(uuidAsString);
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getMethod());
        payment.setStatus(Payment.Status.PENDING);

        // Check if returned error is non-empty, i.e failure
        if (!paymentsDAO.insertPayment(payment).isEmpty()) {
            log.error("Failed to process payment for order {}", paymentRequest.getOrderId());
            payment.setErrorMsg("Payment creation failure");
            payment.setStatus(Payment.Status.FAILED);
        }
        else {
            if(makePayment(payment)) {
                payment.setStatus(Payment.Status.SUCCESSFUL);
            } else {
                payment.setStatus(Payment.Status.FAILED);
            }
        }

        // Record final status
        paymentsDAO.updatePaymentStatus(payment);
        return payment;
    }

    public static void cancelPayment(String orderId) {
        // Cancel Payment in DB
        Payment payment = new Payment();
        paymentsDAO.readPayment(orderId, payment);
        payment.setStatus(Payment.Status.CANCELED);
        paymentsDAO.updatePaymentStatus(payment);
    }

    private static boolean makePayment(Payment payment) {
        if (Objects.equals(payment.getPaymentMethod().getType(), "Credit Card")) {
            PaymentDetails details = payment.getPaymentMethod().getDetails();

            DateFormat dateFormat= new SimpleDateFormat("MM/yyyy");
            Date expiry = new Date();

            try {
                expiry = dateFormat.parse(details.getExpiry());
            } catch (ParseException e) {
                payment.setErrorMsg("Invalid expiry date:" + details.getExpiry());
                return false;
            }

            Date today = new Date();
            if (today.getTime() > expiry.getTime()) {
                payment.setErrorMsg("Expired payment method:" + details.getExpiry());
                return false;
            }

        }
        // Ideally an async call would be made with a callback
        // But, we're skipping that and assuming payment went through
        return true;
    }
}
