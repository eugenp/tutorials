package com.baeldung.jtademo;

import com.baeldung.jtademo.dto.TransferLog;
import com.baeldung.jtademo.services.BankAccountManualTxService;
import com.baeldung.jtademo.services.TellerService;
import com.baeldung.jtademo.services.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JtaDemoApplication.class)
public class JtaDemoUnitTest {
    @Autowired
    TestHelper testHelper;

    @Autowired
    TellerService tellerService;

    @Autowired
    BankAccountManualTxService bankAccountManualTxService;

    @Before
    public void beforeTest() throws Exception {
        testHelper.runAuditDbInit();
        testHelper.runAccountDbInit();
    }

    @Test
    public void whenNoException_thenAllCommitted() throws Exception {
        tellerService.executeTransfer("a0000001", "a0000002", BigDecimal.valueOf(500));

        BigDecimal result = testHelper.balanceOf("a0000001");
        assertThat(testHelper.balanceOf("a0000001")).isEqualByComparingTo(BigDecimal.valueOf(500));
        assertThat(testHelper.balanceOf("a0000002")).isEqualByComparingTo(BigDecimal.valueOf(2500));

        TransferLog lastTransferLog = testHelper.lastTransferLog();
        assertThat(lastTransferLog).isNotNull();
        assertThat(lastTransferLog.getFromAccountId()).isEqualTo("a0000001");
        assertThat(lastTransferLog.getToAccountId()).isEqualTo("a0000002");
        assertThat(lastTransferLog.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(500));
    }

    @Test
    public void whenException_thenAllRolledBack() throws Exception {
        assertThatThrownBy(() -> {
            tellerService.executeTransferFail("a0000002", "a0000001", BigDecimal.valueOf(100));
        }).hasMessage("Something wrong, rollback!");

        assertThat(testHelper.balanceOf("a0000001")).isEqualByComparingTo(BigDecimal.valueOf(1000));
        assertThat(testHelper.balanceOf("a0000002")).isEqualByComparingTo(BigDecimal.valueOf(2000));

        assertThat(testHelper.lastTransferLog()).isNull();
    }

    @Test
    public void givenBMT_whenNoException_thenAllCommitted() throws Exception {
        bankAccountManualTxService.transfer("a0000001", "a0000002", BigDecimal.valueOf(100));

        assertThat(testHelper.balanceOf("a0000001")).isEqualByComparingTo(BigDecimal.valueOf(900));
        assertThat(testHelper.balanceOf("a0000002")).isEqualByComparingTo(BigDecimal.valueOf(2100));
    }
}
