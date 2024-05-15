package com.baeldung.lombok.tostring;

import lombok.ToString;

@ToString
public class Account {

    private String name;

    // render this field before any others (the highest ranked)
    @ToString.Include(rank = 1)
    private String id;

    @ToString.Exclude
    private String accountNumber;

    // automatically excluded
    private String $ignored;

    @ToString.Include
    String description() {
        return "Account description";
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get$ignored() {
        return $ignored;
    }

    public void set$ignored(String value) {
        this.$ignored = value;
    }
}
