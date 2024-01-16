package com.baeldung.objectcopy;

import java.io.*;

class DeepCopyPerson implements Serializable {

    int age;

    public DeepCopyPerson(int age) {
        this.age = age;
    }

    public static DeepCopyPerson deepCopy(DeepCopyPerson person) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(person);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);

        return (DeepCopyPerson) in.readObject();
    }
}
