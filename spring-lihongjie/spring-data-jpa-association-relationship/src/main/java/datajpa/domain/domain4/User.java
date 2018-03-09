package datajpa.domain.domain4;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "user_4")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "ranking")
    private int ranking;
    @Column(name = "admin")
    private boolean admin;

    @OneToMany(mappedBy = "buyer")
    private Set<Item> boughtItems = new HashSet<Item>();

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<Item> getBoughtItems() {
        return boughtItems;
    }

    public void setBoughtItems(Set<Item> boughtItems) {
        this.boughtItems = boughtItems;
    }
}
