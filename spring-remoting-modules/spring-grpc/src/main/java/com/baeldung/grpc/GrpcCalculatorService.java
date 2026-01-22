package com.baeldung.grpc;

import org.springframework.stereotype.Service;
import org.springframework.grpc.calculator.proto.CalculatorGrpc;
import org.springframework.grpc.calculator.proto.Response;
import org.springframework.grpc.calculator.proto.Request;

import io.grpc.stub.StreamObserver;

@Service
public class GrpcCalculatorService extends CalculatorGrpc.CalculatorImplBase {

    @Override
    public void multiply(Request req, StreamObserver<Response> responseObserver) {
        Response reply = Response.newBuilder().setResult(req.getFirstValue() * req.getSecondValue()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
