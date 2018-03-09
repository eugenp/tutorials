package datajpa.domain.domain3;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class DataReadAccess {

    @Id
    @Column(name = "data_read_id")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }
}
