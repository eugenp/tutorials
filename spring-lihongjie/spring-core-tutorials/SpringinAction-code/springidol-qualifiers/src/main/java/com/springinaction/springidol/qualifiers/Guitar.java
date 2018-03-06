package com.springinaction.springidol.qualifiers;

import com.springinaction.springidol.Instrument;

@StringedInstrument
@Strummed
public class Guitar implements Instrument {
  public void play() {
    System.out.println("Strum strum strum");
  }
}
