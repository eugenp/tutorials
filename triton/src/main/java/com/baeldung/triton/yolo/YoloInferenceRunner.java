package com.baeldung.triton.yolo;

import com.google.protobuf.ByteString;
import inference.GRPCInferenceServiceGrpc.GRPCInferenceServiceBlockingStub;
import inference.GrpcService.ModelInferRequest;
import inference.GrpcService.ModelInferRequest.InferInputTensor;
import inference.GrpcService.ModelInferResponse;
import inference.GrpcService.ModelInferResponse.InferOutputTensor;
import inference.GrpcService.InferTensorContents;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class YoloInferenceRunner {
    private final GRPCInferenceServiceBlockingStub blockingStub;
    private final String modelName;

    public YoloInferenceRunner(GRPCInferenceServiceBlockingStub blockingStub, String modelName) {
        this.blockingStub = blockingStub;
        this.modelName = modelName;
    }

    public List<Float> runInference(float[] imageFloatArray) {
        System.out.println("Building inference request for model: " + modelName);

        // 1. Pack the float array into Triton's content container
        InferTensorContents.Builder tensorContents = InferTensorContents.newBuilder();
        for (float pixelValue : imageFloatArray) {
            tensorContents.addFp32Contents(pixelValue);
        }

        // 2. Define the input tensor metadata
        InferInputTensor inputTensor = InferInputTensor.newBuilder()
                .setName("images")
                .setDatatype("FP32")
                .addShape(1)   
                .addShape(3)   
                .addShape(640) 
                .addShape(640) 
                .setContents(tensorContents)
                .build();

        // 3. Build the final request
        ModelInferRequest request = ModelInferRequest.newBuilder()
                .setModelName(modelName)
                .setModelVersion("1")
                .addInputs(inputTensor)
                .build();

        // 4. Execute synchronous gRPC call
        System.out.println("Sending inference request to Triton...");
        ModelInferResponse response = blockingStub.modelInfer(request);

        InferOutputTensor outputTensor = response.getOutputs(0);
        System.out.println("Output Tensor Name: " + outputTensor.getName());

        // 5. EXTRACT FROM RAW BYTES (Triton Performance Optimization)
        ByteString rawData = response.getRawOutputContents(0);
        
        // Wrap the raw bytes in a ByteBuffer (Triton uses Little Endian natively)
        ByteBuffer buffer = rawData.asReadOnlyByteBuffer().order(ByteOrder.LITTLE_ENDIAN);
        
        // Convert the bytes to Floats (4 bytes per float)
        List<Float> resultList = new ArrayList<>(buffer.capacity() / 4);
        while (buffer.hasRemaining()) {
            resultList.add(buffer.getFloat());
        }

        return resultList;
    }
}