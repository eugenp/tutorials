package com.baeldung;

import com.baeldung.adapters.OutputDBAdapter;
import com.baeldung.adapters.OutputFileAdapter;
import com.baeldung.hexagonal_core.EncryptionService;
import com.baeldung.ports.OutputPort;
import org.junit.Test;

public class TestEncryptionService {

    @Test
    public void testEncryptionService_HappyPath_WithFileOutput(){
        OutputPort outputPortFile = new OutputFileAdapter();
        EncryptionService encryptionService = new EncryptionService();
        encryptionService.encryptAndStore("TestEncrypt", outputPortFile);
    }

    @Test
    public void testEncryptionService_HappyPath_WithDBOutput(){
        OutputPort outputPortFile = new OutputDBAdapter();
        EncryptionService encryptionService = new EncryptionService();
        encryptionService.encryptAndStore("TestEncrypt", outputPortFile);
    }
}
