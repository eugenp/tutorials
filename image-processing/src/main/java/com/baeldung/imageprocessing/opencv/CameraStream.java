package com.baeldung.imageprocessing.opencv;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import nu.pattern.OpenCV;
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
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;

public class CameraStream extends Application {
    private VideoCapture capture;

    public void start(Stage stage) throws Exception {
        OpenCV.loadShared();
        capture=  new VideoCapture(0); // The number is the ID of the camera
        ImageView imageView = new ImageView();
        HBox hbox = new HBox(imageView);
        Scene scene = new Scene(hbox);
        stage.setScene(scene);
        stage.show();
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                imageView.setImage(getCapture());
            }
        }.start();
    }

    public Image getCapture() {
        Mat mat = new Mat();
        capture.read(mat);
        return mat2Img(mat);
    }

    public Image getCaptureWithFaceDetection() {
        Mat mat = new Mat();
        capture.read(mat);
        Mat haarClassifiedImg = detectFace(mat);
        return mat2Img(haarClassifiedImg);
    }

    public Image mat2Img(Mat mat) {
        MatOfByte bytes = new MatOfByte();
        Imgcodecs.imencode("img", mat, bytes);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes.toArray());
        Image img = new Image(inputStream); return img;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static Mat detectFace(Mat inputImage) {
        MatOfRect facesDetected = new MatOfRect();
        CascadeClassifier cascadeClassifier = new CascadeClassifier();
        int minFaceSize = Math.round(inputImage.rows() * 0.1f);
        cascadeClassifier.load("./src/main/resources/haarcascades/haarcascade_frontalface_alt.xml");
        cascadeClassifier.detectMultiScale(inputImage,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size()
        );
        Rect[] facesArray =  facesDetected.toArray();
        for(Rect face : facesArray) {
            Imgproc.rectangle(inputImage, face.tl(), face.br(), new Scalar(0, 0, 255), 3 );
        }
        return inputImage;
    }
}