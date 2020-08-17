package com.baeldung.lob;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class FileLocation {

    @Id
    String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
