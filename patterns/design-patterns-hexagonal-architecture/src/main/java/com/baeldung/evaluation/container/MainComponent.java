package com.baeldung.evaluation.container;

import com.baeldung.evaluation.adapters.input.InputCommandsAdapter;
import com.baeldung.evaluation.adapters.output.NotificationsAdapter;
import com.baeldung.evaluation.adapters.output.PersistenceAdapter;
import com.baeldung.evaluation.hexagon.api.IUseCasesApi;
import com.baeldung.evaluation.hexagon.application.Application;
import com.baeldung.evaluation.hexagon.spi.INotificationsSpi;
import com.baeldung.evaluation.hexagon.spi.IPersistenceSpi;

public class MainComponent {
    public static void main(String[] args) {
        IPersistenceSpi persistenceSpi = new PersistenceAdapter();

        INotificationsSpi notificationsSpi = new NotificationsAdapter();

        IUseCasesApi applicationApi = new Application(notificationsSpi, persistenceSpi);

        InputCommandsAdapter inputCommandsAdapter = new InputCommandsAdapter(applicationApi);

        inputCommandsAdapter.execute();
    }
}
