package com.baeldung.adapters;

import com.baeldung.ports.OutputPort;

public class OutputFileAdapter implements OutputPort {

    @Override
    public void storeEncryptedData(String encryptedData) {
        System.out.println("In Output file adapter...");
        // TODO: Write code here to persist to file system

    }
}
