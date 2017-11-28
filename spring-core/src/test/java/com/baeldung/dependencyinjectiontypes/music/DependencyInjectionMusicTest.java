package com.baeldung.dependencyinjectiontypes.music;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.configuration.ApplicationContextTestMusic;

public class DependencyInjectionMusicTest {

    ApplicationContext context;

    @Before
    public void initialTestConfig() {
        context = new AnnotationConfigApplicationContext(ApplicationContextTestMusic.class);
    }

    @Test
    public void givenContainerConfig_WhenOnConstructor_ThenDependencyReady() {

        ConstructorMusicPlayer musicPlayer = (ConstructorMusicPlayer) context.getBean("constructorMusicPlayerBean");

        assertFalse(musicPlayer.play().isEmpty());
    }

    @Test
    public void givenContainerConfig_WhenOnSetter_ThenDependencyReady() {

        SetterMusicPlayer musicPlayer = (SetterMusicPlayer) context.getBean("setterMusicPlayerBean");

        assertFalse(musicPlayer.play().isEmpty());
    }

    @Test
    public void givenContainerConfig_WhenOnAutowired_ThenDependencyReady() {

        AutowiredMusicPlayer musicPlayer = (AutowiredMusicPlayer) context.getBean("autowiredMusicPlayerBean");

        assertFalse(musicPlayer.play().isEmpty());
    }
}
