package com.baeldung.kclkpl;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;

public class IpProcessor implements IRecordProcessor {
    @Override
    public void initialize(InitializationInput initializationInput) { }

    @Override
    public void processRecords(ProcessRecordsInput processRecordsInput) {
        processRecordsInput.getRecords()
            .forEach(record -> System.out.println(new String(record.getData().array())));
    }

    @Override
    public void shutdown(ShutdownInput shutdownInput) { }
}