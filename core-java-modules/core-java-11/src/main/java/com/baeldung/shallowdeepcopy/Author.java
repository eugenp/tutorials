package com.baeldung.shallowdeepcopy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;

@Data
@AllArgsConstructor
public class Author implements Serializable, Cloneable {
    private String name;
    private int age;
    private Book recentBookPublished;

    //Copy Constructor
    public Author(Author copy) {
        this.name = copy.name;
        this.age = copy.age;
        this.recentBookPublished = new Book (copy.recentBookPublished);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException { return super.clone(); }

    public Author shallowCopy() {
        return new Author(name, age, recentBookPublished);
    }

    //Deep copy using copy constructor
    public Author deepCopyUsingCopyConstructor() {
        Author original = new Author(name, age, recentBookPublished);
        return new Author(original);
    }

    // Deep copy using serialization
    public Author deepCopyUsingSerialization() {
        try {
            // Serialize the object
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            out.close();

            // Deserialize the object
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            return (Author) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
