package com.baeldung.grpc.repeated;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ClassLoaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.GeneratedMessageV3;

import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;

public class ProtobufPackedFieldUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(ProtobufPackedFieldUnitTest.class);

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    OrderServiceGrpc.OrderServiceBlockingStub orderClientStub = null;

    static int[] PRODUCT_IDS = null;
    static final String FOLDER_TO_WRITE_OBJECTS = getFolderToWriteObject();

    @AfterAll
    static void clean() {
        File packedFile = new File(FOLDER_TO_WRITE_OBJECTS + "packed_order.bin");
        File unpackedFile = new File(FOLDER_TO_WRITE_OBJECTS + "unpacked_order.bin");
        if (packedFile.delete() && unpackedFile.delete()) {
            logger.info("files {} and {} deleted successfully", "packed_order.bin", "unpacked_order.bin");
        }
    }

    @BeforeEach
    void setup() throws IOException {
        String serverName = InProcessServerBuilder.generateName();
        grpcCleanup.register(
            InProcessServerBuilder.forName(serverName)
                .directExecutor()
                .addService(new OrderService())
                .build()
                .start()
        );
        orderClientStub = OrderServiceGrpc.newBlockingStub(
            grpcCleanup.register(
                InProcessChannelBuilder.forName(serverName)
                    .directExecutor()
                    .build()
            )
        );
        PRODUCT_IDS = fetchProductIds();
    }

    @Test
    void whenUnpackedRepeatedProductIds_thenCreateUnpackedOrderAndInvokeRPC() {
        UnpackedOrder.Builder unpackedOrderBuilder = UnpackedOrder.newBuilder();
        unpackedOrderBuilder.setOrderId(1);
        Arrays.stream(fetchProductIds()).forEach(unpackedOrderBuilder::addProductIds);
        UnpackedOrder unpackedOrderRequest = unpackedOrderBuilder.build();

        UnpackedOrder unpackedOrderResponse = orderClientStub.createOrder(unpackedOrderRequest);

        assertInstanceOf(Integer.class, unpackedOrderResponse.getOrderId());
    }

    @Test
    void whenSerializeUnpackedOrderAndPackedOrderObject_thenSizeofPackedOrderObjectIsLess() throws IOException {
        UnpackedOrder.Builder unpackedOrderBuilder = UnpackedOrder.newBuilder();
        unpackedOrderBuilder.setOrderId(1);
        Arrays.stream(fetchProductIds()).forEach(unpackedOrderBuilder::addProductIds);
        UnpackedOrder unpackedOrder = unpackedOrderBuilder.build();
        String unpackedOrderObjFileName = FOLDER_TO_WRITE_OBJECTS + "unpacked_order.bin";

        serializeObject(unpackedOrderObjFileName, unpackedOrder);

        PackedOrder.Builder packedOrderBuilder = PackedOrder.newBuilder();
        packedOrderBuilder.setOrderId(1);
        Arrays.stream(fetchProductIds()).forEach(packedOrderBuilder::addProductIds);
        PackedOrder packedOrder = packedOrderBuilder.build();
        String packedOrderObjFileName = FOLDER_TO_WRITE_OBJECTS + "packed_order.bin";

        serializeObject(packedOrderObjFileName, packedOrder);

        long sizeOfUnpackedOrderObjectFile = getFileSize(unpackedOrderObjFileName);
        long sizeOfPackedOrderObjectFile = getFileSize(packedOrderObjFileName);
        long sizeReductionPercentage = (sizeOfUnpackedOrderObjectFile - sizeOfPackedOrderObjectFile) * 100/sizeOfUnpackedOrderObjectFile;
        logger.info("Packed field saved {}% over unpacked field", sizeReductionPercentage);
        assertTrue(sizeOfUnpackedOrderObjectFile > sizeOfPackedOrderObjectFile);
    }

    static String getFolderToWriteObject() {
        final String PROTO_FILE = "repeated.proto";
        String FILE_PATH = ClassLoaderUtils.getDefaultClassLoader().getResource(PROTO_FILE).getPath();
        return FILE_PATH.substring(0, FILE_PATH.indexOf(PROTO_FILE));
    }

    private long getFileSize(String fileName) {
        return new File(fileName).length();
    }

    private void serializeObject(String file, GeneratedMessageV3 object) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            object.writeTo(fileOutputStream);
        }
    }

    private int[] fetchProductIds() {
        if (null != PRODUCT_IDS) {
            return PRODUCT_IDS;
        }
        final int ARRAY_SIZE = 20;
        Random random = new Random();
        int[] prodIds = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            prodIds[i] = random.nextInt(900) + 100;
        }
        return prodIds;
    }
}
