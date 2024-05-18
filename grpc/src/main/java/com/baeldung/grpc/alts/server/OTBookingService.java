package com.baeldung.grpc.alts.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.grpc.alts.otbooking.BookingRequest;
import com.baeldung.grpc.alts.otbooking.BookingResponse;
import com.baeldung.grpc.alts.otbooking.OtBookingServiceGrpc;

import io.grpc.stub.StreamObserver;

public class OTBookingService extends OtBookingServiceGrpc.OtBookingServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(OTBookingService.class);

    @Override
    public void getBookingInfo(BookingRequest request, StreamObserver<BookingResponse> responseObserver) {
        logger.info("Received request for booking info for patient {}", request.getPateintID());
        BookingResponse bookingResponse = BookingResponse.newBuilder()
            .setBookingDate("01-Jun-2024 11:30")
            .setCondition("Gall bladder stone")
            .build();
        responseObserver.onNext(bookingResponse);
        responseObserver.onCompleted();
    }
}
