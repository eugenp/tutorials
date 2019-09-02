package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.ports.DataReceiverService;
import com.baeldung.hexagonal.ports.EncoderService;
import com.baeldung.hexagonal.ports.StorageService;

public class DataReceiverServiceImpl implements DataReceiverService {

    EncoderService encoderService;
    StorageService storageService;

    public DataReceiverServiceImpl(EncoderService encoderService, StorageService storageService) {
        this.encoderService = encoderService;
        this.storageService = storageService;
    }

    public void process(int id, Object data) throws Exception {
        String encodedData = encoderService.encode(data);
        storageService.store(id, encodedData);
    }

}
