package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Computer {

    private Processor processor;
    @Autowired
    private Motherboard motherboard;
    private VideoCard videoCard;

    @Autowired
    public Computer(Processor processor) {
        this.processor = processor;
    }

    void printDetails() {
        System.out.println("Computer details");
        System.out.println("Processor: " + processor.getName());
        System.out.println("Motherboard: " + motherboard.getName());
        System.out.println("Video Card: " + videoCard.getName());
        System.out.println("Etc...");
    }

    @Autowired
    public void setVideoCard(VideoCard videoCard) {
        this.videoCard = videoCard;
    }

    Processor getProcessor() {
        return processor;
    }

    Motherboard getMotherboard() {
        return motherboard;
    }

    VideoCard getVideoCard() {
        return videoCard;
    }
}
