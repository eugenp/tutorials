package com.baeldung.grpc.alts.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.grpc.alts.otbooking.BookingRequest;
import com.baeldung.grpc.alts.otbooking.BookingResponse;
import com.baeldung.grpc.alts.otbooking.OtBookingServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.alts.AltsChannelBuilder;

public class AltsOtBookingClient {
    private static final Logger logger = LoggerFactory.getLogger(AltsOtBookingClient.class);

    public static void main(String[] args) {
        final String SERVER_ADDRESS = args[0];
        final String SERVER_ADDRESS_SERVICE_ACCOUNT = args[1];
        ManagedChannel managedChannel = AltsChannelBuilder.forTarget(SERVER_ADDRESS)
            .addTargetServiceAccount(SERVER_ADDRESS_SERVICE_ACCOUNT)
            .build();
        OtBookingServiceGrpc.OtBookingServiceBlockingStub OTBookingServiceStub = OtBookingServiceGrpc.newBlockingStub(
            managedChannel);
        logger.info("RPC initiated successfully to fetch the booking info");
        BookingResponse bookingResponse = OTBookingServiceStub.getBookingInfo(BookingRequest.newBuilder()
            .setPatientID("PT-1204")
            .setDoctorID("DC-3904")
            .build());
        logger.info("RPC response fetched successfully, Date of booking: {}", bookingResponse.getBookingDate());
        managedChannel.shutdown();
    }
}
