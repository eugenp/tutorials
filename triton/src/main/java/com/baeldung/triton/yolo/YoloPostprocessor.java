package com.baeldung.triton.yolo;

import java.util.ArrayList;
import java.util.List;

public class YoloPostprocessor {

    private static final String[] COCO_CLASSES = {
        "person", "bicycle", "car", "motorcycle", "airplane", "bus", "train", "truck", "boat", "traffic light",
        "fire hydrant", "stop sign", "parking meter", "bench", "bird", "cat", "dog", "horse", "sheep", "cow",
        "elephant", "bear", "zebra", "giraffe", "backpack", "umbrella", "handbag", "tie", "suitcase", "frisbee",
        "skis", "snowboard", "sports ball", "kite", "baseball bat", "baseball glove", "skateboard", "surfboard",
        "tennis racket", "bottle", "wine glass", "cup", "fork", "knife", "spoon", "bowl", "banana", "apple",
        "sandwich", "orange", "broccoli", "carrot", "hot dog", "pizza", "donut", "cake", "chair", "couch",
        "potted plant", "bed", "dining table", "toilet", "tv", "laptop", "mouse", "remote", "keyboard", "cell phone",
        "microwave", "oven", "toaster", "sink", "refrigerator", "book", "clock", "vase", "scissors", "teddy bear",
        "hair drier", "toothbrush"
    };

    // Helper class to store a detection box
    private static class Detection {
        int classId;
        float confidence;
        float xMin, yMin, xMax, yMax;

        Detection(int classId, float confidence, float cx, float cy, float w, float h) {
            this.classId = classId;
            this.confidence = confidence;
            // Convert Center X/Y/W/H to Min/Max coordinates for easier overlap calculation
            this.xMin = cx - (w / 2);
            this.yMin = cy - (h / 2);
            this.xMax = cx + (w / 2);
            this.yMax = cy + (h / 2);
        }
    }

    public static void parseAndPrint(List<Float> outputData) {
        System.out.println("Received " + outputData.size() + " data points. Parsing bounding boxes...");
        
        int numClasses = 80;
        int numAnchors = 8400;
        float confidenceThreshold = 0.40f; 
        float iouThreshold = 0.45f; // Threshold for determining if boxes overlap too much

        List<Detection> rawDetections = new ArrayList<>();

        // 1. Extract all boxes passing the initial confidence threshold
        for (int anchor = 0; anchor < numAnchors; anchor++) {
            float maxProb = 0.0f;
            int maxClassId = -1;

            for (int classId = 0; classId < numClasses; classId++) {
                int index = ((4 + classId) * numAnchors) + anchor;
                float prob = outputData.get(index);

                if (prob > maxProb) {
                    maxProb = prob;
                    maxClassId = classId;
                }
            }

            if (maxProb > confidenceThreshold) {
                float cx = outputData.get(0 * numAnchors + anchor);
                float cy = outputData.get(1 * numAnchors + anchor);
                float w = outputData.get(2 * numAnchors + anchor);
                float h = outputData.get(3 * numAnchors + anchor);

                rawDetections.add(new Detection(maxClassId, maxProb, cx, cy, w, h));
            }
        }

        System.out.println("Raw boxes found before NMS: " + rawDetections.size());

        // 2. Perform Non-Maximum Suppression (NMS)
        List<Detection> finalDetections = applyNMS(rawDetections, iouThreshold);

        // 3. Print the final cleaned results
        for (Detection d : finalDetections) {
            String label = (d.classId < COCO_CLASSES.length) ? COCO_CLASSES[d.classId] : "class_" + d.classId;
            System.out.printf("🎯 Detected [%s] (Confidence: %.1f%%) at Box [xMin=%.1f, yMin=%.1f, xMax=%.1f, yMax=%.1f]%n",
                    label, d.confidence * 100, d.xMin, d.yMin, d.xMax, d.yMax);
        }
        
        System.out.println("Final valid objects detected: " + finalDetections.size());
    }

    // NMS Algorithm logic
    private static List<Detection> applyNMS(List<Detection> detections, float iouThreshold) {
        // Sort detections by confidence (highest first)
        detections.sort((a, b) -> Float.compare(b.confidence, a.confidence));

        List<Detection> keep = new ArrayList<>();
        boolean[] suppressed = new boolean[detections.size()];

        for (int i = 0; i < detections.size(); i++) {
            if (suppressed[i]) continue;
            
            Detection current = detections.get(i);
            keep.add(current);

            for (int j = i + 1; j < detections.size(); j++) {
                if (suppressed[j]) continue;
                
                Detection next = detections.get(j);
                
                // Only suppress boxes of the same class that overlap significantly
                if (current.classId == next.classId) {
                    if (calculateIoU(current, next) > iouThreshold) {
                        suppressed[j] = true; 
                    }
                }
            }
        }
        return keep;
    }

    // Calculates Intersection over Union (IoU) between two bounding boxes
    private static float calculateIoU(Detection box1, Detection box2) {
        float xOverlap = Math.max(0, Math.min(box1.xMax, box2.xMax) - Math.max(box1.xMin, box2.xMin));
        float yOverlap = Math.max(0, Math.min(box1.yMax, box2.yMax) - Math.max(box1.yMin, box2.yMin));
        
        float intersectionArea = xOverlap * yOverlap;
        
        float box1Area = (box1.xMax - box1.xMin) * (box1.yMax - box1.yMin);
        float box2Area = (box2.xMax - box2.xMin) * (box2.yMax - box2.yMin);
        
        float unionArea = box1Area + box2Area - intersectionArea;
        
        // Prevent division by zero
        if (unionArea == 0) return 0;
        
        return intersectionArea / unionArea;
    }
}