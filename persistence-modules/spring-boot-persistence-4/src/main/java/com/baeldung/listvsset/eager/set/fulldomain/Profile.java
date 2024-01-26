package com.baeldung.listvsset.eager.set.fulldomain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import java.util.Objects;

@Entity
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

    public Profile() {
    }

    public Long getId() {
        return this.id;
    }

    public String getBiography() {
        return this.biography;
    }

    public String getWebsite() {
        return this.website;
    }

    public String getProfilePictureUrl() {
        return this.profilePictureUrl;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Profile profile = (Profile) o;

        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String toString() {
        return "Profile(id=" + this.getId() + ", biography=" + this.getBiography() + ", website=" + this.getWebsite()
               + ", profilePictureUrl=" + this.getProfilePictureUrl() + ", user=" + this.getUser() + ")";
    }
}

