package com.baeldung.imageprocessing.opencv;

import java.io.ByteArrayInputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import javafx.scene.image.Image;

public class FaceDetection {

public static void main(String[] args) {
    // Load the native library.
    System.load("/opencv/build/lib/libopencv_java4100.dylib");
    detectFace(FaceDetection.class.getClassLoader().getResource("portrait.jpg").getPath(),
        "./processed.jpg");
}

    public static Mat loadImage(String imagePath) {
        return Imgcodecs.imread(imagePath);
    }

    public static void saveImage(Mat imageMatrix, String targetPath) {
        Imgcodecs.imwrite(targetPath, imageMatrix);
    }

    public static void detectFace(String sourceImagePath, String targetImagePath) {
        Mat loadedImage = loadImage(sourceImagePath);
        MatOfRect facesDetected = new MatOfRect();
        CascadeClassifier cascadeClassifier = new CascadeClassifier();
        int minFaceSize = Math.round(loadedImage.rows() * 0.1f);
        String filename = FaceDetection.class.getClassLoader().getResource("haarcascades/haarcascade_frontalface_alt.xml").getFile();
        cascadeClassifier.load(filename);
        cascadeClassifier.detectMultiScale(loadedImage,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size());
        Rect[] facesArray =  facesDetected.toArray();
        for(Rect face : facesArray) {
            Imgproc.rectangle(loadedImage, face.tl(), face.br(), new Scalar(0, 0, 255), 10);
        }
        saveImage(loadedImage, targetImagePath);
    }

    public Image mat2Img(Mat mat) {
        MatOfByte bytes = new MatOfByte();
        Imgcodecs.imencode("img", mat, bytes);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes.toArray());
        Image img = new Image(inputStream); return img;
    }

}