package com.baeldung.hexagonal;

import com.baeldung.hexagonal.application.TextInfoServiceImpl;
import com.baeldung.hexagonal.domain.InputService;
import com.baeldung.hexagonal.domain.OutputService;
import com.baeldung.hexagonal.domain.TextInfoService;
import com.baeldung.hexagonal.infrastructure.FileInputService;
import com.baeldung.hexagonal.infrastructure.FileOutputService;

public class HexagonalMain {

    static String inputFileName = "inputfile";
    static String outputFileName = "outputfile";
    
    public static void main(String[] args) {    
        InputService inputService = new FileInputService(inputFileName);
        OutputService outputService = new FileOutputService(outputFileName);
        TextInfoService textInfoService = new TextInfoServiceImpl(inputService, outputService);
        textInfoService.run();
    }

}
