package com.baeldung.lombok.intro;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Delegate;

@Entity
@Getter @Setter @NoArgsConstructor // <--- THIS is it
@ToString(exclude = {"events"})
public class User implements Serializable, HasContactInformation {

    private @Id @Setter(AccessLevel.PROTECTED) Long id; // will be set when persisting

    private String nickname;

    // Whichever other User-specific attributes

    @Delegate(types = {HasContactInformation.class})
    private final ContactInformationSupport contactInformation = new ContactInformationSupport();

    // User itelf will implement all contact information by delegation

    @OneToMany(mappedBy = "user")
    private List<UserEvent> events;

    public User(String nickname, String firstName, String lastName, String phoneNr) {
        this.nickname = nickname;
        contactInformation.setFirstName(firstName);
        contactInformation.setLastName(lastName);
        contactInformation.setPhoneNr(phoneNr);
    }

}
