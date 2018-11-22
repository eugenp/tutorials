package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.ConsoleExtractorAdapter;
import com.baeldung.hexagonal.adapters.ConsoleLoaderAdapter;

public class Application {
    public static void main(String args[]) {
        ExtractorPort extractorPort = new ConsoleExtractorAdapter();
        LoaderPort loaderPort = new ConsoleLoaderAdapter();

        MyEtlService service = new MyEtlService();

        service.etl(extractorPort, loaderPort);
    }
}
