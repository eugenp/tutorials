package com.springinaction.springidol;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringIdolConfig {
  // <start id="duke_bean_javaconfig" />
  @Bean
  public Performer duke() {
    return new Juggler();
  }
  // <end id="duke_bean_javaconfig" />

  // <start id="duke15_bean_javaconfig" />
  @Bean
  public Performer duke15() {
    return new Juggler(15);
  }

  // <end id="duke15_bean_javaconfig" />

  // <start id="poeticduke_bean_javaconfig" />
  @Bean
  public Performer poeticDuke() {
    return new PoeticJuggler(15, sonnet29());
  }

  @Bean
  private Poem sonnet29() {
    return new Sonnet29();
  }

  // <end id="poeticduke_bean_javaconfig" />

  // <start id="kenny_bean_javaconfig" />
  @Bean
  public Performer kenny() {
    Instrumentalist kenny = new Instrumentalist();
    kenny.setSong("Jingle Bells");
    return kenny;
  }
  // <end id="kenny_bean_javaconfig" />
}
