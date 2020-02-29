package com.baeldung.hexagonal_core;

import com.baeldung.ports.OutputPort;

public class EncryptionService {

    public void encryptAndStore(String input, OutputPort outputPort) {
        outputPort.storeEncryptedData(encryptInputData(input));
    }

    private static String encryptInputData(String inputDataStr) {
        int characterAsciiValue = 0;
        StringBuilder encryptedOutput = new StringBuilder();
        for (int iCount = 0; iCount < inputDataStr.length(); iCount++) {
            characterAsciiValue = inputDataStr.charAt(iCount);
            characterAsciiValue = ~characterAsciiValue;
            encryptedOutput.append(characterAsciiValue);
        }
        return encryptedOutput.toString();
    }

}