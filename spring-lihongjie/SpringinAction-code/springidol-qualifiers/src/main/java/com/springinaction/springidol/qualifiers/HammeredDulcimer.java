package com.springinaction.springidol.qualifiers;

import com.springinaction.springidol.Instrument;

@StringedInstrument
public class HammeredDulcimer implements Instrument {
  public void play() {
    System.out.println("Ting ting ting");
  }
}
