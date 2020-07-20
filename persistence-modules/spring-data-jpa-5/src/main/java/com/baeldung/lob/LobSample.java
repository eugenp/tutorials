package com.baeldung.lob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
class LobSample {

    @Id
    @GeneratedValue
    Long id;

    @Lob
    byte[] image;

    @Lob
    char[] bigText;

    @Lob
    String bigStringText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public char[] getBigText() {
        return bigText;
    }

    public void setBigText(char[] bigText) {
        this.bigText = bigText;
    }

    public String getBigStringText() {
        return bigStringText;
    }

    public void setBigStringText(String bigStringText) {
        this.bigStringText = bigStringText;
    }

}
