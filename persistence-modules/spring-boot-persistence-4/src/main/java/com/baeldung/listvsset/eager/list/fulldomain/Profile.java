package com.baeldung.listvsset.eager.list.fulldomain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Profile {

    @Id
    private Long id;

    @Lob
    private String biography;
    private String website;
    private String profilePictureUrl;

    @JsonBackReference
    @OneToOne(mappedBy = "profile")
    @JoinColumn(unique = true)
    private User user;
}

