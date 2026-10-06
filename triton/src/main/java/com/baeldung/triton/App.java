package com.baeldung.triton;

import com.baeldung.triton.client.TritonClientManager;
import com.baeldung.triton.yolo.ImagePreprocessor;
import com.baeldung.triton.yolo.YoloInferenceRunner;
import com.baeldung.triton.yolo.YoloPostprocessor;

import java.util.List;

public class App {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8001;

        /* Exported Yolo model for object detection */
        String modelName = "yolo_onnx";

        /* Copy sample.jpeg at path src/main/resources*/
        String imageFile = "sample.jpeg"; 
        
        System.out.println("Connecting to Triton Inference Server at " + host + ":" + port);
        TritonClientManager clientManager = new TritonClientManager(host, port);
        
        try {
            if (!clientManager.isServerLive()) {
                System.err.println("Triton Server is not live. Make sure your Docker container is running.");
                return;
            }
            System.out.println("Triton Server is live and ready.");
            
            // 1. Preprocess the real image
            float[] inputTensor = ImagePreprocessor.preprocessFromResources(imageFile, 640, 640);
            
            // 2. Run Inference over gRPC
            YoloInferenceRunner runner = new YoloInferenceRunner(clientManager.getStub(), modelName);
            List<Float> outputs = runner.runInference(inputTensor);
            
            // 3. Post-process and print detections
            YoloPostprocessor.parseAndPrint(outputs);
            
        } catch (Exception e) {
            System.err.println("Execution failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            clientManager.shutdown();
        }
    }
}