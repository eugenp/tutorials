package com.baeldung.jpa.returnmultipleentities;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ReturnMultipleEntitiesIntegrationTest {
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;
    private ReportRepository reportRepository;

    @Before
    public void setup() {
        factory = Persistence.createEntityManagerFactory("jpa-h2-return-multiple-entities");
        entityManager = factory.createEntityManager();
        reportRepository = new ReportRepository();
        populateH2DB();
    }

    @Test
    public void whenQueryingForMultipleEntitiesInOneQuery_thenJPAReturnsMultipleEntitiesInCorrectOrder() {
        List<Object[]> reportDetails = reportRepository.find("user1@gmail.com");

        assertEquals(2, reportDetails.size());

        for (Object[] reportDetail : reportDetails) {
            assertEquals(3, reportDetail.length);

            Channel channel = (Channel) reportDetail[0];
            Subscription subscription = (Subscription) reportDetail[1];
            User user = (User) reportDetail[2];

            assertEquals("single", subscription.getCode());
            assertEquals("user1@gmail.com", user.getEmail());
            if (!("eurosport".equals(channel.getCode()) || "hbo".equals(channel.getCode()))) {
                fail();
            }
        }
    }

    private static void populateH2DB() {
        entityManager.getTransaction().begin();

        Subscription single = new Subscription();
        single.setCode("single");

        Subscription family = new Subscription();
        family.setCode("family");

        entityManager.persist(single);
        entityManager.persist(family);

        Channel bbc = new Channel();
        bbc.setCode("bbc");
        bbc.setSubscriptionId(family.getId());

        Channel eurosport = new Channel();
        eurosport.setCode("eurosport");
        eurosport.setSubscriptionId(single.getId());

        Channel hbo = new Channel();
        hbo.setCode("hbo");
        hbo.setSubscriptionId(single.getId());

        entityManager.persist(bbc);
        entityManager.persist(eurosport);
        entityManager.persist(hbo);

        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        user1.setSubscriptionId(single.getId());

        entityManager.persist(user1);

        entityManager.getTransaction().commit();
    }
}
