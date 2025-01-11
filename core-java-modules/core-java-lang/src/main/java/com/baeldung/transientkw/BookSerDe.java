package com.baeldung.transientkw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BookSerDe {
    static String fileName = "book.ser";

    /**
     * Method to serialize Book objects to the file
     * @throws FileNotFoundException 
     */
    public static void serialize(Book book) throws Exception {
        FileOutputStream file = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(book);

        out.close();
        file.close();
    }

    /**
     * Method to deserialize the person object
     * @return book
     * @throws IOException, ClassNotFoundException
     */
    public static Book deserialize() throws Exception {
        FileInputStream file = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(file);

        Book book = (Book) in.readObject();

        in.close();
        file.close();

        return book;
    }

}
