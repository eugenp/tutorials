package datajpa.domain.domain3;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = LAZY, cascade = MERGE, mappedBy = "clients")
    @Fetch(FetchMode.SELECT)
//    @JoinTable(name = "user_account_client", joinColumns = {@JoinColumn(name = "client_id")},
//            inverseJoinColumns = {@JoinColumn(name = "user_account_id")})
    private List<UserAccount> userAccounts = new ArrayList<UserAccount>();

    @ManyToMany(fetch = LAZY, cascade = MERGE)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "client_read_data", joinColumns = {@JoinColumn(name = "client_id")},
            inverseJoinColumns = {@JoinColumn(name = "data_read_id")})
    private Set<DataReadAccess> dataReadAccesses = new HashSet<DataReadAccess>();

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

    public Set<DataReadAccess> getDataReadAccesses() {
        return dataReadAccesses;
    }

    public void setDataReadAccesses(Set<DataReadAccess> dataReadAccesses) {
        this.dataReadAccesses = dataReadAccesses;
    }

    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    public void addDataReadAccess(DataReadAccess dataReadAccess) {
        dataReadAccesses.add(dataReadAccess);
    }

    public void addUserAccont(UserAccount userAccount) {
        userAccounts.add(userAccount);
        userAccount.getClients().add(this);
    }
}
