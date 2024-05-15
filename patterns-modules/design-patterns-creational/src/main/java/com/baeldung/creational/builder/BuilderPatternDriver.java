package com.baeldung.creational.builder;

public class BuilderPatternDriver {
    public static void main(String[] args) {
        BankAccount newAccount = new BankAccount
          .BankAccountBuilder("Jon", "22738022275")
          .withEmail("jon@example.com")
          .wantNewsletter(true)
          .build();

        System.out.println("Name: " + newAccount.getName());
        System.out.println("AccountNumber:" + newAccount.getAccountNumber());
        System.out.println("Email: " + newAccount.getEmail());
        System.out.println("Want News letter?: " + newAccount.isNewsletter());
    }
}
