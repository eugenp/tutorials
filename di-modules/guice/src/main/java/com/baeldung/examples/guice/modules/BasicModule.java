
package com.baeldung.examples.guice.modules;

import com.baeldung.examples.guice.Communication;
import com.baeldung.examples.guice.CommunicationMode;
import com.baeldung.examples.guice.DefaultCommunicator;
import com.baeldung.examples.guice.EmailCommunicationMode;
import com.baeldung.examples.guice.IMCommunicationMode;
import com.baeldung.examples.guice.SMSCommunicationMode;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author baeldung
 */
public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        try {
            bind(Communication.class).toConstructor(Communication.class.getConstructor(Boolean.class));
            bind(Boolean.class).toInstance(true);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(com.baeldung.examples.guice.binding.BasicModule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(com.baeldung.examples.guice.binding.BasicModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        bind(DefaultCommunicator.class).annotatedWith(Names.named("AnotherCommunicator")).to(DefaultCommunicator.class).asEagerSingleton();

        bind(CommunicationMode.class).annotatedWith(Names.named("IMComms")).to(IMCommunicationMode.class);
        bind(CommunicationMode.class).annotatedWith(Names.named("EmailComms")).to(EmailCommunicationMode.class);
        bind(CommunicationMode.class).annotatedWith(Names.named("SMSComms")).to(SMSCommunicationMode.class);
    }

}
