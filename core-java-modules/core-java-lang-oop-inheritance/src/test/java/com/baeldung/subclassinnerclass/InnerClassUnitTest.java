package com.baeldung.subclassinnerclass;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;

public class InnerClassUnitTest {
    @Test
    public void givenInnerStaticClassWhenInstantiatedThenOuterClassIsInstantiated() {
        Notifier emailNotifier = new EmailNotifier();
        EmailNotifier.EmailConnector emailConnector = new EmailNotifier.EmailConnector();

        assertThat(emailNotifier).hasSameClassAs(new EmailNotifier());
        assertThat(emailConnector).isInstanceOf(EmailNotifier.EmailConnector.class);
    }
}
