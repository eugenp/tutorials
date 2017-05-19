package com.readlearncode.stateless;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class StatelessEJBTest {

    @Inject
    private EJBClient1 ejbClient1;

    @Inject
    private EJBClient2 ejbClient2;

    @Test
    public void givenOneStatelessBean_whenStateIsSetInOneBean_secondBeanShouldHaveSameState() {

        // act
        ejbClient1.statelessEJB.name = "Client 1";

        // assert
        Assert.assertEquals("Client 1", ejbClient1.statelessEJB.name);
        Assert.assertEquals("Client 1", ejbClient2.statelessEJB.name);

    }


    @Test
    public void givenOneStatelessBean_whenStateIsSetInBothBeans_secondBeanShouldHaveSecondBeanState() {

        // act
        ejbClient1.statelessEJB.name = "Client 1";
        ejbClient2.statelessEJB.name = "Client 2";

        // assert
        Assert.assertEquals("Client 2", ejbClient2.statelessEJB.name);

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(StatelessEJB.class)
                .addClass(EJBClient1.class)
                .addClass(EJBClient2.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
