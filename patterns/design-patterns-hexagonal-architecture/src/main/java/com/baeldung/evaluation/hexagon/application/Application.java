package com.baeldung.evaluation.hexagon.application;

import com.baeldung.evaluation.hexagon.api.IApplicationApi;
import com.baeldung.evaluation.hexagon.spi.INotificationsSpi;
import com.baeldung.evaluation.hexagon.spi.IPersistenceSpi;

public class Application implements IApplicationApi {
        private final INotificationsSpi notificationsSpi;
        private final IPersistenceSpi persistenceSpi;

        public Application(INotificationsSpi notificationsSpi, IPersistenceSpi persistenceSpi) {
                this.notificationsSpi = notificationsSpi;
                this.persistenceSpi = persistenceSpi;
        }

        public boolean execute(String command){
                System.out.println(String.format("Executing command: '%s'", command));

                System.out.println("Saving command output ...");
                persistenceSpi.persist();

                System.out.println("Sending notifications ...");
                notificationsSpi.send();

                return true;
        }
}
