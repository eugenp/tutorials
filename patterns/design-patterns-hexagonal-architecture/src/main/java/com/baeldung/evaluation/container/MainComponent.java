package com.baeldung.evaluation.container;

import com.baeldung.evaluation.adapters.input.RealInputAdapter;
import com.baeldung.evaluation.adapters.output.RealNotificationsAdapter;
import com.baeldung.evaluation.adapters.output.RealPersistenceAdapter;
import com.baeldung.evaluation.hexagon.api.IUseCasesApi;
import com.baeldung.evaluation.hexagon.application.Application;
import com.baeldung.evaluation.hexagon.spi.INotificationsSpi;
import com.baeldung.evaluation.hexagon.spi.IPersistenceSpi;

public class MainComponent {

        // assume this can be done through a configurable DI framework
        static IUseCasesApi applicationApi = null;
        static RealInputAdapter realInputAdapter = null;
        static INotificationsSpi notificationsSpi = null;
        static IPersistenceSpi persistenceSpi = null;

        public static void main(String[] args) {
                persistenceSpi = new RealPersistenceAdapter();
                notificationsSpi = new RealNotificationsAdapter();
                applicationApi = new Application(notificationsSpi, persistenceSpi);
                realInputAdapter = new RealInputAdapter(applicationApi);

                realInputAdapter.execute();
        }
}
