package com.baeldung.schemageneration.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "email_address")
    private String emailAddress;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountSetting> accountSettings = new ArrayList<>();

    public Account() {
    }

    public Account(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<AccountSetting> getAccountSettings() {
        return accountSettings;
    }

    public void setAccountSettings(List<AccountSetting> accountSettings) {
        this.accountSettings = accountSettings;
    }

    public void addAccountSetting(AccountSetting setting) {
        this.accountSettings.add(setting);
        setting.setAccount(this);
    }
}
