package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.UseCaseInputAdapter;
import com.baeldung.hexagonal.adapters.input.RealInputAdapter;
import com.baeldung.hexagonal.adapters.input.TestInputAdapter;
import com.baeldung.hexagonal.adapters.output.MockNotificationsAdapter;
import com.baeldung.hexagonal.adapters.output.MockPersistenceAdapter;
import com.baeldung.hexagonal.adapters.output.RealNotificationsAdapter;
import com.baeldung.hexagonal.adapters.output.RealPersistenceAdapter;
import com.baeldung.hexagonal.api.IApplicationApi;
import com.baeldung.hexagonal.application.Application;
import com.baeldung.hexagonal.spi.INotificationsSpi;
import com.baeldung.hexagonal.spi.IPersistenceSpi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class HexagonalArchitectureUnitTest {
        IApplicationApi applicationApi = null;
        INotificationsSpi notificationsSpi = null;
        IPersistenceSpi persistenceSpi = null;

        @Test
        public void givenStep1_whenExistsTestInputAdapterAndMockedOutputAdapter_followTheConsole(){
                System.out.println("*** " +
                    new Throwable()
                        .getStackTrace()[0]
                        .getMethodName());

                persistenceSpi = new MockPersistenceAdapter();
                notificationsSpi = new MockNotificationsAdapter();
                applicationApi = new Application(notificationsSpi, persistenceSpi);
                UseCaseInputAdapter inputRealAdapter = new TestInputAdapter(applicationApi);

                inputRealAdapter.execute();
        }

        @Test
        public void givenStep2_whenExistsRealInputAdapterAndMockedOutputAdapter_followTheConsole(){
                System.out.println("*** " +
                    new Throwable()
                    .getStackTrace()[0]
                    .getMethodName());

                persistenceSpi = new MockPersistenceAdapter();
                notificationsSpi = new MockNotificationsAdapter();
                applicationApi = new Application(notificationsSpi, persistenceSpi);
                UseCaseInputAdapter inputRealAdapter = new RealInputAdapter(applicationApi);

                inputRealAdapter.execute();
        }

        @Test
        public void givenStep3_whenExistsTestInputAdapterAndRealOutputAdapter_followTheConsole(){
                System.out.println("*** " +
                    new Throwable()
                        .getStackTrace()[0]
                        .getMethodName());

                persistenceSpi = new RealPersistenceAdapter();
                notificationsSpi = new RealNotificationsAdapter();
                applicationApi = new Application(notificationsSpi, persistenceSpi);
                UseCaseInputAdapter inputRealAdapter = new TestInputAdapter(applicationApi);

                inputRealAdapter.execute();
        }

        @Test
        public void givenStep4_whenExistsRealInputAdapterAndRealOutputAdapter_followTheConsole(){
                System.out.println("*** " +
                    new Throwable()
                        .getStackTrace()[0]
                        .getMethodName());

                persistenceSpi = new RealPersistenceAdapter();
                notificationsSpi = new RealNotificationsAdapter();
                applicationApi = new Application(notificationsSpi, persistenceSpi);
                UseCaseInputAdapter inputRealAdapter = new TestInputAdapter(applicationApi);

                inputRealAdapter.execute();
        }
}
