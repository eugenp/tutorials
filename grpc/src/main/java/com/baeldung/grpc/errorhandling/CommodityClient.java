package com.baeldung.grpc.errorhandling;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.grpc.errorhandling.CommodityPriceProviderGrpc.CommodityPriceProviderStub;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.rpc.Code;
import com.google.rpc.ErrorInfo;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class CommodityClient {

    private static final Logger logger = LoggerFactory.getLogger(CommodityClient.class.getName());

    private final CommodityPriceProviderStub nonBlockingStub;

    public CommodityClient(Channel channel) {

        nonBlockingStub = CommodityPriceProviderGrpc.newStub(channel);
    }

    public void getBidirectionalCommodityPriceLists() throws InterruptedException {

        logger.info("#######START EXAMPLE#######: BidirectionalStreaming - getCommodityPriceLists from list of commodities");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<StreamingCommodityQuote> responseObserver = new StreamObserver<StreamingCommodityQuote>() {
            @Override
            public void onNext(StreamingCommodityQuote streamingCommodityQuote) {

                switch (streamingCommodityQuote.getMessageCase()) {
                case COMODITY_QUOTE:
                    CommodityQuote commodityQuote = streamingCommodityQuote.getComodityQuote();
                    logger.info("RESPONSE producer:" + commodityQuote.getCommodityName() + " price:" + commodityQuote.getPrice());
                    break;
                case STATUS:
                    com.google.rpc.Status status = streamingCommodityQuote.getStatus();
                    logger.info("RESPONSE status error:");
                    logger.info("Status code:" + Code.forNumber(status.getCode()));
                    logger.info("Status message:" + status.getMessage());
                    for (Any any : status.getDetailsList()) {
                        if (any.is(ErrorInfo.class)) {
                            ErrorInfo errorInfo;
                            try {
                                errorInfo = any.unpack(ErrorInfo.class);
                                logger.info("Reason:" + errorInfo.getReason());
                                logger.info("Domain:" + errorInfo.getDomain());
                                logger.info("Insert Token:" + errorInfo.getMetadataMap()
                                    .get("insertToken"));
                            } catch (InvalidProtocolBufferException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    }
                    break;
                default:
                    logger.info("Unknow message case");
                }
            }

            @Override
            public void onCompleted() {
                logger.info("Finished getBidirectionalCommodityPriceLists");
                finishLatch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                logger.error("getBidirectionalCommodityPriceLists Failed:" + Status.fromThrowable(t));
                finishLatch.countDown();
            }
        };
        StreamObserver<Commodity> requestObserver = nonBlockingStub.bidirectionalListOfPrices(responseObserver);
        try {
            for (int i = 1; i <= 2; i++) {
                Commodity request = Commodity.newBuilder()
                    .setCommodityName("Commodity" + i)
                    .setAccessToken(i + "23validToken")
                    .build();
                logger.info("REQUEST - commodity:" + request.getCommodityName());
                requestObserver.onNext(request);
                Thread.sleep(200);
                if (finishLatch.getCount() == 0) {
                    return;
                }
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }
        requestObserver.onCompleted();

        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            logger.info("getBidirectionalCommodityPriceLists can not finish within 1 minute");
        }
    }

    public static void main(String[] args) throws InterruptedException, InvalidProtocolBufferException {

        String target = "localhost:8980";
        if (args.length > 0) {
            target = args[0];
        }

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
            .usePlaintext()
            .build();
        try {
            CommodityClient client = new CommodityClient(channel);

            client.getBidirectionalCommodityPriceLists();

        } finally {
            channel.shutdownNow()
                .awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
