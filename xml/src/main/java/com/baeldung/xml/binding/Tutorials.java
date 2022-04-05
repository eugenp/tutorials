package com.baeldung.xml.binding;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tutorials {

    private List<Tutorial> tutorial;

    public List<Tutorial> getTutorial() {
        return tutorial;
    }

    @XmlElement
    public void setTutorial(List<Tutorial> tutorial) {
        this.tutorial = tutorial;
    }

}
