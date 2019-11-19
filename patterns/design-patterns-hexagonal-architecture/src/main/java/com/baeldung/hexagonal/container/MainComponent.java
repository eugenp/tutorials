package com.baeldung.hexagonal.container;

import com.baeldung.hexagonal.adapters.input.RealInputAdapter;
import com.baeldung.hexagonal.adapters.output.RealNotificationsAdapter;
import com.baeldung.hexagonal.adapters.output.RealPersistenceAdapter;
import com.baeldung.hexagonal.api.IApplicationApi;
import com.baeldung.hexagonal.application.Application;
import com.baeldung.hexagonal.spi.INotificationsSpi;
import com.baeldung.hexagonal.spi.IPersistenceSpi;

public class MainComponent {

        // assume this can be done through a configurable DI framework
        static IApplicationApi applicationApi = null;
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
