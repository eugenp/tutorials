package com.baeldung.adapters;

import com.baeldung.ports.OutputPort;

public class OutputDBAdapter implements OutputPort {
    @Override
    public void storeEncryptedData(String encryptedData) {
        System.out.println("In Output DB adapter...");
        // TODO: Write code here to persist to in-memory H2
    }
}
