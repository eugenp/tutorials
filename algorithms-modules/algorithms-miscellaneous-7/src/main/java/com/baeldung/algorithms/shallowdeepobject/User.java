package com.baeldung.algorithms.shallowdeepobject;


public class User implements Cloneable{

    public String name;
    public int matricNumber;
    public Address address;

    public User(String name, int matricNumber, Address address){
        this.name = name;
        this.matricNumber = matricNumber;
        this.address = address;
    }

    @Override
    public String toString(){
        return "Name: "+ name + ", Matriculation Number: " + matricNumber + ", Address: " + address;
    }

    @Override
    public Object clone() throws CloneNotSuppportedException{
        User user = null;
        try{
            user = (User) super.clone();
        }catch(CloneNotSupportedException e){
            System.out.println("Clonable type not supported");
        }

        user.address = (Address) this.address.clone();
        return user;
    }
}