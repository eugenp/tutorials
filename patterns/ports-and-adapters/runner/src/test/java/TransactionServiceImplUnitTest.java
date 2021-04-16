import com.baeldung.pattern.portsAndAdapters.controllers.TransactionController;
import com.baeldung.pattern.portsAndAdapters.core.model.Transaction;
import com.baeldung.pattern.portsAndAdapters.core.ports.TransactionService;
import com.baeldung.pattern.portsAndAdapters.info.TransactionInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Date;

public class TransactionServiceImplUnitTest {

    private TransactionController controller;
    private TransactionService transactionService;

    @Before
    public void setUp() {
        transactionService = Mockito.mock(TransactionService.class);
        controller = new TransactionController(transactionService);
    }

    @Test
    public void whenAddingTransaction_thenTransactionIsStored() {
        TransactionInfo testTransactionInfo = new TransactionInfo("12.99", "Random purchase");
        Transaction testTransaction = new Transaction("Random purchase", 12.99, new Date());

        Mockito.when(transactionService.add(testTransaction)).thenReturn(0);

        int index = controller.saveNewTransaction(testTransactionInfo);

        Assertions.assertEquals(index, 0);
    }

    @Test
    public void whenAddingDuplicateTransaction_thenOnlyFirstIsStored() {
        TransactionInfo testTransactionInfo = new TransactionInfo("12.99", "Random purchase");
        TransactionInfo testTransactionInfo2 = new TransactionInfo("12.99", "Random purchase");

        Transaction testTransaction = new Transaction("Random purchase", 12.99, new Date());
        Transaction testTransaction2 = new Transaction("Random purchase", 12.99, new Date());

        Mockito.when(transactionService.add(testTransaction)).thenReturn(0);
        Mockito.when(transactionService.add(testTransaction2)).thenReturn(0);

        int index = controller.saveNewTransaction(testTransactionInfo);
        int index2 = controller.saveNewTransaction(testTransactionInfo2);

        Assertions.assertEquals(index, 0);
        Assertions.assertEquals(index2, 0);
    }
}
