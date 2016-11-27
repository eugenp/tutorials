package com.baeldung.domain;

import com.baeldung.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * A user.
 */
@Entity @Table(name = "jhi_user") @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

    @NotNull @Pattern(regexp = Constants.LOGIN_REGEX) @Size(min = 1, max = 50) @Column(length = 50, unique = true, nullable = false) private String login;

    @JsonIgnore @NotNull @Size(min = 60, max = 60) @Column(name = "password_hash", length = 60) private String password;

    @Size(max = 50) @Column(name = "first_name", length = 50) private String firstName;

    @Size(max = 50) @Column(name = "last_name", length = 50) private String lastName;

    @Email @Size(max = 100) @Column(length = 100, unique = true) private String email;

    @Email @Size(max = 20) @Column(length = 20) private String phoneNumber;

    @NotNull @Column(nullable = false) private boolean activated = false;

    @Size(min = 2, max = 5) @Column(name = "lang_key", length = 5) private String langKey;

    @Size(max = 20) @Column(name = "activation_key", length = 20) @JsonIgnore private String activationKey;

    @Size(max = 20) @Column(name = "reset_key", length = 20) private String resetKey;

    @Column(name = "reset_date", nullable = true) private ZonedDateTime resetDate = null;

    @JsonIgnore @ManyToMany @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }) @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) private Set<Authority> authorities = new HashSet<>();

    @JsonIgnore @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user") @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) private Set<PersistentToken> persistentTokens = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    //Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = login.toLowerCase(Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    public void setResetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<PersistentToken> getPersistentTokens() {
        return persistentTokens;
    }

    public void setPersistentTokens(Set<PersistentToken> persistentTokens) {
        this.persistentTokens = persistentTokens;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!login.equals(user.login)) {
            return false;
        }

        return true;
    }

    @Override public int hashCode() {
        return login.hashCode();
    }

    @Override public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            '}';
    }
}
