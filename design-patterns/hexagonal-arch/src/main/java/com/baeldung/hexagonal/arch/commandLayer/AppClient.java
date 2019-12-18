package com.baeldung.hexagonal.arch.commandLayer;

public class AppClient {
    public static void main(String[] args) {
    	UserChoice choice = new UserChoiceImpl();
    	choice.getInput();    	    
    }
}
