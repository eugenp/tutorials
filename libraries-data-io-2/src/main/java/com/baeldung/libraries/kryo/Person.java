package com.baeldung.libraries.kryo;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.Date;

@DefaultSerializer(PersonSerializer.class)
public class Person implements KryoSerializable {
    private String name = "John Doe";
    private int age = 18;
    private Date birthDate = new Date(933191282821L);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeString(name);
        output.writeLong(birthDate.getTime());
        output.writeInt(age);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        name = input.readString();
        birthDate = new Date(input.readLong());
        age = input.readInt();
    }

}
