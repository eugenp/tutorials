package com.baeldung.creationaldp.builder;

public class BankAccount {
    private String name;
    private String accountNumber;
    private int age;
    private String email;
    private boolean newsletter;

    //The constructor that takes a builder from which it will create object
    //the access to this is only provided to builder
    private BankAccount(BankAccountBuilder builder) {
        this.name = builder.name;
        this.accountNumber = builder.accountNumber;
        this.age = builder.age;
        this.email = builder.email;
        this.newsletter = builder.newsletter;
    }
    
    public static class BankAccountBuilder {
        private String name;
        private String accountNumber;
        private int age;
        private String email;
        private boolean newsletter;
        
        //All Mandatory parameters goes with this constructor
        public BankAccountBuilder(String name, String accountNumber) {
            this.name = name;
            this.accountNumber = accountNumber;
        }

        //setters for optional parameters
        public BankAccountBuilder withAge(int age) {
            this.age = age;
            return this;
        }
        
        public BankAccountBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public BankAccountBuilder wantNewsletter(boolean newsletter) {
            this.newsletter = newsletter;
            return this;
        }
        
        //the actual build method that prepares and returns a BankAccount object
        public BankAccount build() {
            return new BankAccount(this);
        }
    }

    //getters
    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public boolean isNewsletter() {
        return newsletter;
    }
}
