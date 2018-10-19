package com.baeldung.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.baeldung.xml.binding.Tutorial;
import com.baeldung.xml.binding.Tutorials;

public class JaxbParser {

    private File file;

    public JaxbParser(File file) {
        this.file = file;
    }

    public Tutorials getFullDocument() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Tutorials.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Tutorials tutorials = (Tutorials) jaxbUnmarshaller.unmarshal(this.getFile());
            return tutorials;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createNewDocument() {
        Tutorials tutorials = new Tutorials();
        tutorials.setTutorial(new ArrayList<Tutorial>());
        Tutorial tut = new Tutorial();
        tut.setTutId("01");
        tut.setType("XML");
        tut.setTitle("XML with Jaxb");
        tut.setDescription("XML Binding with Jaxb");
        tut.setDate("04/02/2015");
        tut.setAuthor("Jaxb author");
        tutorials.getTutorial().add(tut);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Tutorials.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(tutorials, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
