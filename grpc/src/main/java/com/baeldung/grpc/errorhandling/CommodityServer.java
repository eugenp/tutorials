package com.baeldung.grpc.errorhandling;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Any;
import com.google.rpc.Code;
import com.google.rpc.ErrorInfo;

import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;

public class CommodityServer {

    private static final Logger logger = LoggerFactory.getLogger(CommodityServer.class.getName());
    private final int port;
    private final Server server;
    private static Map<String, Double> commodityLookupBasePrice;
    static {
        commodityLookupBasePrice = new ConcurrentHashMap<>();
        commodityLookupBasePrice.put("Commodity1", 5.0);
        commodityLookupBasePrice.put("Commodity2", 6.0);
    }

    public static void main(String[] args) throws Exception {

        CommodityServer commodityServer = new CommodityServer(8980);
        commodityServer.start();
        if (commodityServer.server != null) {
            commodityServer.server.awaitTermination();
        }
    }

    public CommodityServer(int port) throws IOException {
        this.port = port;
        server = ServerBuilder.forPort(port)
            .addService(new CommodityService())
            .build();
    }

    public void start() throws IOException {
        server.start();
        logger.info("Server started, listening on {}", port);
        Runtime.getRuntime()
            .addShutdownHook(new Thread() {
                @Override
                public void run() {
                    System.err.println("shutting down server");
                    try {
                        CommodityServer.this.stop();
                    } catch (InterruptedException e) {
                        e.printStackTrace(System.err);
                    }
                    System.err.println("server shutted down");
                }
            });
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown()
                .awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public static class CommodityService extends CommodityPriceProviderGrpc.CommodityPriceProviderImplBase {

        @Override
        public void getBestCommodityPrice(Commodity request, StreamObserver<CommodityQuote> responseObserver) {

            if (commodityLookupBasePrice.get(request.getCommodityName()) == null) {

                Metadata.Key<ErrorResponse> errorResponseKey = ProtoUtils.keyForProto(ErrorResponse.getDefaultInstance());
                ErrorResponse errorResponse = ErrorResponse.newBuilder()
                    .setCommodityName(request.getCommodityName())
                    .setAccessToken(request.getAccessToken())
                    .setExpectedValue("Only Commodity1, Commodity2 are supported")
                    .build();
                Metadata metadata = new Metadata();
                metadata.put(errorResponseKey, errorResponse);
                responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("The commodity is not supported")
                    .asRuntimeException(metadata));
            } else if (request.getAccessToken().equals("123validToken") == false) {

                com.google.rpc.Status status = com.google.rpc.Status.newBuilder()
                    .setCode(Code.NOT_FOUND.getNumber())
                    .setMessage("The access token not found")
                    .addDetails(Any.pack(ErrorInfo.newBuilder()
                        .setReason("Invalid Token")
                        .setDomain("com.baeldung.grpc.errorhandling")
                        .putMetadata("insertToken", "123validToken")
                        .build()))
                    .build();
                responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            } else {
                CommodityQuote commodityQuote = CommodityQuote.newBuilder()
                    .setPrice(fetchBestPriceBid(request))
                    .setCommodityName(request.getCommodityName())
                    .setProducerName("Best Producer with best price")
                    .build();
                responseObserver.onNext(commodityQuote);
                responseObserver.onCompleted();
            }
        }

        @Override
        public StreamObserver<Commodity> bidirectionalListOfPrices(StreamObserver<StreamingCommodityQuote> responseObserver) {

            return new StreamObserver<Commodity>() {
                @Override
                public void onNext(Commodity request) {

                    logger.info("Access token:{}", request.getAccessToken());
                    if (request.getAccessToken()
                        .equals("123validToken") == false) {

                        com.google.rpc.Status status = com.google.rpc.Status.newBuilder()
                            .setCode(Code.NOT_FOUND.getNumber())
                            .setMessage("The access token not found")
                            .addDetails(Any.pack(ErrorInfo.newBuilder()
                                .setReason("Invalid Token")
                                .setDomain("com.baeldung.grpc.errorhandling")
                                .putMetadata("insertToken", "123validToken")
                                .build()))
                            .build();
                        StreamingCommodityQuote streamingCommodityQuote = StreamingCommodityQuote.newBuilder()
                            .setStatus(status)
                            .build();
                        responseObserver.onNext(streamingCommodityQuote);
                    } else {

                        for (int i = 1; i <= 5; i++) {
                            CommodityQuote commodityQuote = CommodityQuote.newBuilder()
                                .setPrice(fetchProviderPriceBid(request, "producer:" + i))
                                .setCommodityName(request.getCommodityName())
                                .setProducerName("producer:" + i)
                                .build();
                            StreamingCommodityQuote streamingCommodityQuote = StreamingCommodityQuote.newBuilder()
                                .setComodityQuote(commodityQuote)
                                .build();
                            responseObserver.onNext(streamingCommodityQuote);
                        }
                    }
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }

                @Override
                public void onError(Throwable t) {
                    logger.info("error:{}", t.getMessage());
                }
            };
        }

    }

    private static double fetchBestPriceBid(Commodity commodity) {

        return commodityLookupBasePrice.get(commodity.getCommodityName()) + ThreadLocalRandom.current()
            .nextDouble(-0.2d, 0.2d);
    }

    private static double fetchProviderPriceBid(Commodity commodity, String providerName) {

        return commodityLookupBasePrice.get(commodity.getCommodityName()) + providerName.length() + ThreadLocalRandom.current()
            .nextDouble(-0.2d, 0.2d);
    }
}
