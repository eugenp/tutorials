package com.baeldung.creationaldp.abstractfactory;

public class ToyFactory implements AbstractFactory {

    @Override
    public Toy getToy(String toyType) {
          if("Dog".equalsIgnoreCase(toyType)){
             return new Dog();
          } 
          else if("Duck".equalsIgnoreCase(toyType)){
             return new Duck();
          }
          
          return null;
    }

    @Override
    public Color getColor(String color) {
        throw new UnsupportedOperationException();
    }

}
