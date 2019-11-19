package com.baeldung.evaluation;

import com.baeldung.evaluation.adapters.UseCaseInputAdapter;
import com.baeldung.evaluation.adapters.input.RealInputAdapter;
import com.baeldung.evaluation.adapters.input.TestInputAdapter;
import com.baeldung.evaluation.adapters.output.MockNotificationsAdapter;
import com.baeldung.evaluation.adapters.output.MockPersistenceAdapter;
import com.baeldung.evaluation.adapters.output.RealNotificationsAdapter;
import com.baeldung.evaluation.adapters.output.RealPersistenceAdapter;
import com.baeldung.evaluation.hexagon.api.IUseCasesApi;
import com.baeldung.evaluation.hexagon.application.Application;
import com.baeldung.evaluation.hexagon.spi.INotificationsSpi;
import com.baeldung.evaluation.hexagon.spi.IPersistenceSpi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class HexagonalArchitectureUnitTest {
        IUseCasesApi applicationApi = null;
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
