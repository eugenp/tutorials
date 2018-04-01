// <start id="piano_java" />
package com.springinaction.springidol;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("woodwind")
public class Saxophone implements Instrument {
  public Saxophone() {
  }

  public void play() {
    System.out.println("PLINK PLINK PLINK");
  }
}
// <end id="piano_java" />
