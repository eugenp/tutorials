package com.baeldung.hexagonalarchitecture.useraccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 17:03
 */
@Entity
public class UserAccountEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private boolean emailConfirmed;
    private String verificationToken;
    private boolean active;
    private boolean blocked;

    public UserAccountEntity() {
    }

    private UserAccountEntity(Long id, String username, String password, String email, boolean emailConfirmed,
                              String verificationToken, boolean active, boolean blocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.emailConfirmed = emailConfirmed;
        this.verificationToken = verificationToken;
        this.active = active;
        this.blocked = blocked;
    }

    public static UserAccountEntity of(UserAccount userAccount) {
        return new UserAccountEntity(userAccount.getId(), userAccount.getUsername(), userAccount.getPassword(),
                userAccount.getEmail().getEmail(), userAccount.getEmail().isConfirmed(), userAccount.getVerificationToken()
                , userAccount.isActive(), userAccount.isBlocked());
    }


    public UserAccount getUserAccount() {
        return UserAccount.of(this.id, this.username, this.password, UserAccountEmail.of(this.email, this.emailConfirmed),
                this.verificationToken, this.active, this.blocked);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isBlocked() {
        return blocked;
    }


    @Override
    public String toString() {
        return "UserAccountEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", emailConfirmed=" + emailConfirmed +
                ", verificationToken='" + verificationToken + '\'' +
                ", active=" + active +
                ", blocked=" + blocked +
                '}';
    }
}
