package com.baeldung.hexagonal.adaptor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.hexagonal.core.BankAccountService;
import com.baeldung.hexagonal.core.Transaction;
import com.baeldung.hexagonal.port.web.WebInterface;

/**
 * This is a simplified skeleton of a Web View that may be a set of MVC
 * controllers
 * 
 * @author ysharma2512
 *
 */
@Controller
public class WebAdaptorPort implements WebInterface {

    @Autowired
    BankAccountService coreService;

    public WebAdaptorPort(BankAccountService coreService) {
        this.coreService = coreService;
    }

    @Override
    @RequestMapping(value = "/showBalance", method = RequestMethod.GET)
    public ResponseEntity<String> showBalance() {
        // adapt to Web Interface
        return ResponseEntity.ok(coreService.getBalance()
            .toPlainString());
    }

    @Override
    @RequestMapping(value = "/renderStatement", method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> renderStatement() {
        // return the list
        return ResponseEntity.ok(coreService.getStatement());
    }

    @Override
    @RequestMapping(value = "/postDeposit", method = RequestMethod.POST)
    public ResponseEntity<String> postDeposit(BigDecimal amount) {
        // return the deposit status to Web interface
        return ResponseEntity.ok(Boolean.valueOf(coreService.deposit(amount))
            .toString());
    }

    @Override
    @RequestMapping(value = "/postWithdrawal", method = RequestMethod.POST)
    public ResponseEntity<String> postWithdrawal(BigDecimal amount) {
        // return the withdrawal status to web interface
        return ResponseEntity.ok(Boolean.valueOf(coreService.withDraw(amount))
            .toString());

    }

}
