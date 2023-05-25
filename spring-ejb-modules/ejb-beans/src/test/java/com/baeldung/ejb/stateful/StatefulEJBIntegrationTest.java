package com.baeldung.ejb.stateful;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.baeldung.ejb.stateful.EJBClient1;
import com.baeldung.ejb.stateful.EJBClient2;
import com.baeldung.ejb.stateful.StatefulEJB;

import javax.inject.Inject;


@RunWith(Arquillian.class)
public class StatefulEJBIntegrationTest {

    @Inject
    private EJBClient1 ejbClient1;

    @Inject
    private EJBClient2 ejbClient2;

    @Test
    public void givenOneStatefulBean_whenTwoClientsSetValueOnBean_thenClientStateIsMaintained() {

        // act
        ejbClient1.statefulEJB.name = "Client 1";
        ejbClient2.statefulEJB.name = "Client 2";

        // assert
        Assert.assertNotEquals(ejbClient1.statefulEJB.name, ejbClient2.statefulEJB.name);
        Assert.assertEquals("Client 1", ejbClient1.statefulEJB.name);
        Assert.assertEquals("Client 2", ejbClient2.statefulEJB.name);

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(StatefulEJB.class)
                .addClass(EJBClient1.class)
                .addClass(EJBClient2.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
