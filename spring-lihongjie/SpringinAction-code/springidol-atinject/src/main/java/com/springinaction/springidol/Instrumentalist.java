//<start id="instrumentalist_java" /> 
package com.springinaction.springidol;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component("eddie")
public class Instrumentalist implements Performer {
  // ...
//<end id="instrumentalist_java" />
  public void perform() throws PerformanceException {
    instrument.play();
  }

  //<start id="autowire_property" /> 
  @Inject
  private Instrument instrument;
  //<end id="autowire_property" />

  public Instrument getInstrument() {
    return instrument;
  }
//<start id="instrumentalist_java_2" /> 
}
//<end id="instrumentalist_java_2" />
