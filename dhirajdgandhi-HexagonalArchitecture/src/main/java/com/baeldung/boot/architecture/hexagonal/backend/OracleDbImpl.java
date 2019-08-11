package com.baeldung.boot.architecture.hexagonal.backend;
import com.baeldung.boot.architecture.hexagonal.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
    value = "db.h2",
    havingValue = "false"
)
@Component
public class OracleDbImpl implements IDBPort {

    Logger LOG = LoggerFactory.getLogger(OracleDbImpl.class);

    @Override
    public int addTransaction(Transaction transaction) {
        //Implement your functionality here
        LOG.info("CURRENTLY NOT SUPPORTING ORACLEDB IMPLEMENTATION");
        return -1;
    }

    @Override
    public Transaction getTransaction(int transactionId) {
        //Implement your functionality here
        LOG.info("CURRENTLY NOT SUPPORTING ORACLEDB IMPLEMENTATION");
        return null;
    }
}
