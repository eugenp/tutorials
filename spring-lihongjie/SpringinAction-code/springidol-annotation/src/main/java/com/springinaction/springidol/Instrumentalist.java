//<start id="instrumentalist_java" />
package com.springinaction.springidol;

import org.springframework.beans.factory.annotation.Autowired;

public class Instrumentalist implements Performer {
  //<start id="autowire_constructor" />
  @Autowired
  public Instrumentalist(Instrument instrument) {
    this.instrument = instrument;
  }

  //<end id="autowire_constructor" />

  public void perform() throws PerformanceException {
    System.out.print("Playing " + song + " : ");
    instrument.play();
  }

  private String song;

  public void setSong(String song) {
    this.song = song;
  }

  public String getSong() {
    return song;
  }

  //<start id="autowire_property" />
  @Autowired
  private Instrument instrument;

  //<end id="autowire_property" />

  //<start id="autowire_nonsetter" />
  @Autowired
  public void heresYourInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  //<end id="autowire_nonsetter" />

  //<start id="autowire_setter" />
  @Autowired
  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  //<end id="autowire_setter" />

  public Instrument getInstrument() {
    return instrument;
  }
}
//<end id="instrumentalist_java" />
