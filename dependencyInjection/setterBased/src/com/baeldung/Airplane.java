package com.baeldung;

public class Airplane {
   private Aileron Aileron;

   // a setter method to inject the dependency.
   public void setAileron(Aileron Aileron) {
      System.out.println("Inside setAileron." );
      this.Aileron = Aileron;
   }
   // a getter method to return Aileron
   public Aileron getAileron() {
      return Aileron;
   }

   public void aileronCheck() {
      Aileron.checkAileron();
   }
}
