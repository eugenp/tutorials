package com.baeldung.triton;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.triton.client.TritonClientManager;
import com.baeldung.triton.yolo.ImagePreprocessor;
import com.baeldung.triton.yolo.YoloInferenceRunner;
import com.baeldung.triton.yolo.YoloPostprocessor;

import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Please start the Triton Inference Server Docker container 
 * tand ensure it is running on localhost:8001 with the "yolo_onnx" model loaded.
 */
public class TritonInferenceLiveTest {

    private static TritonClientManager clientManager;

    @BeforeAll
    public static void setup() {
        // Connect to the local Docker container
        clientManager = new TritonClientManager("localhost", 8001);
    }

    @AfterAll
    public static void teardown() {
        if (clientManager != null) {
            // Gracelfully shutdown the local Docker container
            clientManager.shutdown();
        }
    }

    @Test
    public void givenTritonServerRunning_whenCheckingLiveness_thenLiveIsTrue() {
        assertTrue(clientManager.isServerLive(), 
          "Triton Server should be live. Ensure the Docker container is running on port 8001.");
    }

    @Test
    public void givenValidImage_whenRunningInference_thenReturnsDetections() throws Exception {
        // Preprocess the sample image
        float[] inputTensor = ImagePreprocessor.preprocessFromResources("sample.jpeg", 640, 640);
        
        // Run inference
        YoloInferenceRunner runner = new YoloInferenceRunner(clientManager.getStub(), "yolo_onnx");
        List<Float> outputs = runner.runInference(inputTensor);
        
        // Verify we received data
        assertFalse(outputs.isEmpty(), "Inference output should not be empty");
        
        // YOLOv8 natively outputs a matrix of [1, 84, 8400] which flattens to 705,600 elements
        assertEquals(705600, outputs.size(), 
          "Output tensor should contain exactly 705,600 float elements");

        // Run the post-processor so the test logs print the NMS cat detection!
        YoloPostprocessor.parseAndPrint(outputs);
    }
}