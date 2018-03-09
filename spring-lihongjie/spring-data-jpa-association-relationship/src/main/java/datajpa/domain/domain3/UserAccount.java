package datajpa.domain.domain3;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.EAGER;

@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = EAGER, cascade = MERGE)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "user_account_client", joinColumns = {
            @JoinColumn(name = "user_account_id")}, inverseJoinColumns = {
            @JoinColumn(name = "client_id")})
    Set<Client> clients = new HashSet<Client>();

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

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        clients.add(client);
        client.getUserAccounts().add(this);
    }
}
