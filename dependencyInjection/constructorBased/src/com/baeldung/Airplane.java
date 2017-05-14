package com.baeldung;

public class Airplane {
   private Aileron Aileron;

   public Airplane(Aileron Aileron) {
      System.out.println("Inside Airplane constructor." );
      this.Aileron = Aileron;
   }
   public void aileronCheck() {
      Aileron.checkAileron();
   }
}
