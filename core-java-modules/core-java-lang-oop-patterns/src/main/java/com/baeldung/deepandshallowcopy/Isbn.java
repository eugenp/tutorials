package com.baeldung.deepandshallowcopy;

public class Isbn implements Cloneable{
    private String isbn;

    @Override
    public Object clone()
    {
        Isbn clonedIsbn = null;
        try{
            clonedIsbn = (Isbn) super.clone();
        }catch (CloneNotSupportedException e){
            clonedIsbn = new Isbn(this.getIsbn());
        }
        return clonedIsbn;
    }

    public Isbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
