package com.baeldung.configuration;

import com.baeldung.dependencyinjectiontypes.music.AutowiredMusicPlayer;
import com.baeldung.dependencyinjectiontypes.music.ConstructorMusicPlayer;
import com.baeldung.dependencyinjectiontypes.music.SetterMusicPlayer;
import com.baeldung.dependencyinjectiontypes.music.Song;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextTestMusic {

    @Bean
    public Song songBean() {
        return new Song();
    }
    
    @Bean
    public ConstructorMusicPlayer constructorMusicPlayerBean() {
        return new ConstructorMusicPlayer(songBean());
    }
    
    @Bean
    public SetterMusicPlayer setterMusicPlayerBean() {
        SetterMusicPlayer musicPlayer = new SetterMusicPlayer();
        musicPlayer.setSongToPlay(songBean());
        return musicPlayer;
    }
    
    @Bean
    public AutowiredMusicPlayer autowiredMusicPlayerBean() {
        return new AutowiredMusicPlayer();
    }
}
