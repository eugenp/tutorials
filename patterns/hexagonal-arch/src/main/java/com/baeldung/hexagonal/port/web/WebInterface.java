package com.baeldung.hexagonal.port.web;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.baeldung.hexagonal.core.Transaction;

/**
 * The interface or port methods methods 
 * @author ysharma2512
 *
 */
public interface WebInterface {
    public ResponseEntity<String> showBalance();

    public ResponseEntity<List<Transaction>> renderStatement();

    public ResponseEntity<String> postDeposit(BigDecimal amount);

    public ResponseEntity<String> postWithdrawal(BigDecimal amount);
}
