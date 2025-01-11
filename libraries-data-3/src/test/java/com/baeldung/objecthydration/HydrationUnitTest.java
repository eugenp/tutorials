package com.baeldung.objecthydration;

import org.junit.Assert;
import org.junit.Test;

public class HydrationUnitTest {

    @Test
    public void givenEmptyClass_whenLazilyinitialised_thenGiveNullOnGet() {
        User iamUser = new User();
        Assert.assertNull(iamUser.getuId());
    }

    @Test
    public void givenInitialisedClass_withLazilyInitialised_thenDoesNotGiveNullOnGet() {
        User iamUser = new User();
        iamUser.setAlias("007");
        Assert.assertNotNull(iamUser.getAlias());
    }

    @Test
    public void givenEmptyClass_withHydration_thenShouldHaveAllAttributesSet() {
        User jamesBond = new User();
        Assert.assertNull(jamesBond.getAlias());

        jamesBond.generateMyUser();
        Assert.assertEquals("007", jamesBond.getAlias());
    }

    @Test
    public void givenUser_thenShouldPerformUserSerialisationDeserialisation() {
        User iamUser = new User();
        iamUser.setAlias("007");
        UserSerialisationDeserialisation usd = new UserSerialisationDeserialisation();
        usd.serialisedUser(iamUser, "bond.ser");
        User deserialisedUser = usd.deserialiseUser("bond.ser");
        Assert.assertEquals(iamUser.getAlias(), deserialisedUser.getAlias());

    }
}
