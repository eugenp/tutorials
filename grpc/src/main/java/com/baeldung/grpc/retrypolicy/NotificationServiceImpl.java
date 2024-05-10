package com.baeldung.grpc.retrypolicy;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.grpc.retry.NotificationRequest;
import com.baeldung.grpc.retry.NotificationResponse;
import com.baeldung.grpc.retry.NotificationServiceGrpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class NotificationServiceImpl extends NotificationServiceGrpc.NotificationServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public void notify(NotificationRequest request, StreamObserver<NotificationResponse> responseObserver) {
        String message = request.getMessage();
        String msgType = request.getType();
        int msgID = request.getMessageID();
        logger.info("message:{}\tmessage type:{}\tmessage id:{}", message, msgType, msgID);

        String response = new StringBuilder().append("Message received: ")
            .append(msgType)
            .append(" - ")
            .append(message)
            .toString();

        if (!saveAndSendMsg(msgID, response)) {
            responseObserver.onError(Status.UNAVAILABLE.withDescription("Service temporarily unavailable...")
                .asRuntimeException());
            logger.error("Service temporarily unavailable would go for retry if the policy permits");
        } else {
            NotificationResponse notificationResponse = NotificationResponse.newBuilder()
                .setResponse(response)
                .build();
            responseObserver.onNext(notificationResponse);
            responseObserver.onCompleted();
        }
    }

    /**
     * created for demo purpose, it would return true only when the generated random number is a prime number
     * @param msgID
     * @param response
     * @return
     */
    private boolean saveAndSendMsg(int msgID, String response) {
        boolean result = false;
        int randomNum = new Random().nextInt(100) + 1;
        if (isPrime(randomNum)) {
            result = true;
        }
        logger.error("Will work only when the random number {} is prime", randomNum);
        return result;
    }

    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
