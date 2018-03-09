package datajpa.domain.domain5;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "USER_5")
public class User {
  @Id
  @GeneratedValue
  @Column(name = "USER_ID")
  private Long id;

  @Embedded
  @AttributeOverrides(
      {
          @AttributeOverride(name="firstname", column = @Column(name = "firstname")),
          @AttributeOverride(name="lastname", column = @Column(name = "lastname"))
      }
  )
  private Person person;

  public Person getPerson() {

    return person;
  }

  public void setPerson(Person person) {

    this.person = person;
  }

  public Long getId() {

    return id;
  }

  public void setId(Long id) {

    this.id = id;
  }
}
