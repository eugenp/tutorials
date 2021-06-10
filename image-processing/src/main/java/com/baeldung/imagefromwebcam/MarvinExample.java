package com.baeldung.imagefromwebcam;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.video.MarvinJavaCVAdapter;
import marvin.video.MarvinVideoInterface;
import marvin.video.MarvinVideoInterfaceException;

public class MarvinExample {

    public static void main(String[] args) throws MarvinVideoInterfaceException {
        MarvinVideoInterface videoAdapter = new MarvinJavaCVAdapter();
        videoAdapter.connect(0);
        MarvinImage image = videoAdapter.getFrame();
        MarvinImageIO.saveImage(image, "selfie.jpg");
    }

    public void captureWithPanel() throws MarvinVideoInterfaceException {
        MarvinVideoInterface videoAdapter = new MarvinJavaCVAdapter();
        videoAdapter.connect(0);
        MarvinImage image = videoAdapter.getFrame();

        MarvinImagePanel videoPanel = new MarvinImagePanel();
        videoPanel.setImage(image);

        videoPanel.setSize(800,600);
        videoPanel.setVisible(true);
    }

}
