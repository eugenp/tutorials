package com.baeldung.jtademo;

import com.baeldung.jtademo.dto.TransferLog;
import com.baeldung.jtademo.services.AuditService;
import com.baeldung.jtademo.services.BankAccountService;
import com.baeldung.jtademo.services.TellerService;
import com.baeldung.jtademo.services.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JtaDemoApplication.class)
public class JtaDemoUnitTest {
    @Autowired
    TestHelper testHelper;

    @Autowired
    TellerService tellerService;

    @Autowired
    BankAccountService accountService;

    @Autowired
    AuditService auditService;

    @BeforeEach
    public void beforeTest() throws Exception {
        testHelper.runAuditDbInit();
        testHelper.runAccountDbInit();
    }

    @Test
    public void givenAnnotationTx_whenNoException_thenAllCommitted() {
        tellerService.executeTransfer("a0000001", "a0000002", BigDecimal.valueOf(500));

        assertThat(accountService.balanceOf("a0000001")).isEqualByComparingTo(BigDecimal.valueOf(500));
        assertThat(accountService.balanceOf("a0000002")).isEqualByComparingTo(BigDecimal.valueOf(2500));

        TransferLog lastTransferLog = auditService.lastTransferLog();

        assertThat(lastTransferLog.getFromAccountId()).isEqualTo("a0000001");
        assertThat(lastTransferLog.getToAccountId()).isEqualTo("a0000002");
        assertThat(lastTransferLog.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(500));
    }

    @Test
    public void givenAnnotationTx_whenException_thenAllRolledBack() {
        assertThatThrownBy(
          () -> tellerService.executeTransfer("a0000002", "a0000001", BigDecimal.valueOf(100000))
        ).hasMessage("Insufficient fund.");

        assertThat(accountService.balanceOf("a0000001")).isEqualByComparingTo(BigDecimal.valueOf(1000));
        assertThat(accountService.balanceOf("a0000002")).isEqualByComparingTo(BigDecimal.valueOf(2000));
        assertThat(auditService.lastTransferLog()).isNull();
    }

    @Test
    public void givenProgrammaticTx_whenCommit_thenAllCommitted() throws Exception {
        tellerService.executeTransferProgrammaticTx("a0000001", "a0000002", BigDecimal.valueOf(500));

        assertThat(accountService.balanceOf("a0000001")).isEqualByComparingTo(BigDecimal.valueOf(500));
        assertThat(accountService.balanceOf("a0000002")).isEqualByComparingTo(BigDecimal.valueOf(2500));

        TransferLog lastTransferLog = auditService.lastTransferLog();

        assertThat(lastTransferLog.getFromAccountId()).isEqualTo("a0000001");
        assertThat(lastTransferLog.getToAccountId()).isEqualTo("a0000002");
        assertThat(lastTransferLog.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(500));
    }

    @Test
    public void givenProgrammaticTx_whenRollback_thenAllRolledBack() {
        assertThatThrownBy(
          () -> tellerService.executeTransferProgrammaticTx("a0000002", "a0000001", BigDecimal.valueOf(100000))
        ).hasMessage("Insufficient fund.");

        assertThat(accountService.balanceOf("a0000001")).isEqualByComparingTo(BigDecimal.valueOf(1000));
        assertThat(accountService.balanceOf("a0000002")).isEqualByComparingTo(BigDecimal.valueOf(2000));
        assertThat(auditService.lastTransferLog()).isNull();
    }
}
