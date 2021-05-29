package com.baeldung.pattern.hexagonal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.baeldung.pattern.hexagonal.domain.BusinessLogic;
import com.baeldung.pattern.hexagonal.domain.ExternalServicePort;
import com.baeldung.pattern.hexagonal.domain.UIActionPort;
import com.baeldung.pattern.hexagonal.serverside.FileBasedExternalServiceAdapter;
import com.baeldung.pattern.hexagonal.serverside.RestAPIExternalServiceAdapter;
import com.baeldung.pattern.hexagonal.ui.ConsoleUIAdapter;
import org.junit.jupiter.api.Test;

public class HexagonalArchitectureIntegrationTest {

    @Test
    void testConsoleUiAdapterWithFileBasedService() {
        ExternalServicePort fileAdapter = new FileBasedExternalServiceAdapter();

        UIActionPort businessLogic = new BusinessLogic(fileAdapter);

        ConsoleUIAdapter uiAdapter = new ConsoleUIAdapter(businessLogic);

        assertEquals("Called from the Console Adapter context. Fetched from file Based adapter", uiAdapter.display());
    }

    @Test
    void testConsoleUiAdapterWithRestApiService() {
        ExternalServicePort fileAdapter = new RestAPIExternalServiceAdapter();

        UIActionPort businessLogic = new BusinessLogic(fileAdapter);

        ConsoleUIAdapter uiAdapter = new ConsoleUIAdapter(businessLogic);

        assertEquals("Called from the Console Adapter context. Fetched from REST API based adapter", uiAdapter.display());
    }

}
