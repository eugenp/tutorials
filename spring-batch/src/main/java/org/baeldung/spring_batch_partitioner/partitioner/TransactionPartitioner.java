package org.baeldung.spring_batch_partitioner.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class TransactionPartitioner implements Partitioner {
    
    private static final Log log = LogFactory.getLog(TransactionPartitioner.class);

    public Map<String, ExecutionContext> partition(int range) {
        Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
        for (int i = 1; i <= range; i++) {
            ExecutionContext exContext = new ExecutionContext();
            log.info("Starting : Thread" + i);
            exContext.put("name", "Thread" + i);
            result.put("partition" + i, exContext);
        }
        return result;
    }

}
