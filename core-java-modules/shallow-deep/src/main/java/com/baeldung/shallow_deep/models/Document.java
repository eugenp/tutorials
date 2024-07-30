package com.baeldung.shallow_deep;

import java.io.Serializable;

public class Document implements Serializable {
    private int id;
    private DocDescription docDescription;

    public Document(int id, DocDescription docDescription) {
        this.id = id;
        this.docDescription = docDescription;
    }

    public Document(Document document) {
        this.id = document.id;
        this.docDescription = document.docDescription;
    }

    public int getId() {
        return id;
    }

    public DocDescription getDocDescription() {
        return docDescription;
    }

    @Override
    public String toString() {
        return "{" + id + ", " + docDescription + "}";
    }
}


