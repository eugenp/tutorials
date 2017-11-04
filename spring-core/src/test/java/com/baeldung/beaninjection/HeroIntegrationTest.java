package com.baeldung.beaninjection;

import com.baeldung.beaninjection.config.HeroConfiguration;
import com.baeldung.beaninjection.domain.Hero;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = {HeroConfiguration.class, Hero.class} )
public class HeroIntegrationTest {

    @Autowired
    private Hero hero;

    @Test
    public void givenInjectedHero_whenSwordAndShieldAreProvided_thenSwordAndShieldAreNotNull() throws Exception {
        assertNotNull(hero.getShield());
        assertNotNull(hero.getSword());
    }

    @Test
    public void givenInjectedHero_whenArmorIsNotProvided_thenHeroIsCreatedWithNullArmor() throws Exception {
        assertNull(hero.getArmor());
    }
}
