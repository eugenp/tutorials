package datajpa.domain.domain5;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Person {

  @Column(name = "firstname", nullable = false)
  private String firstname;

  @Column(name = "lastname", nullable = false)
  private String lastname;

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
}
