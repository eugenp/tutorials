package com.baeldung.milvus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.baeldung.utility.JsonUtility;

import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.common.DataType;
import io.milvus.v2.common.IndexParam;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import io.milvus.v2.service.collection.request.DropCollectionReq;
import io.milvus.v2.service.collection.request.HasCollectionReq;
import io.milvus.v2.service.vector.request.DeleteReq;
import io.milvus.v2.service.vector.request.InsertReq;
import io.milvus.v2.service.vector.response.DeleteResp;
import io.milvus.v2.service.vector.response.InsertResp;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class MilvusVectorDBIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(MilvusVectorDBIntegrationTest.class);

    private static final String CONNECTION_URI = "https://in03-9078dd4edbed8ef.api.gcp-us-west1.zillizcloud.com";
    private static final String API_KEY = "c9563b286ee36287454a4121de92b6524e66351be3f09976cfe0e22013efe3c505a4122be126badae9669c730c0f6e55ac706740";

    private static MilvusClientV2 milvusClientV2 = null;

    @BeforeAll
    static void setup() {
        milvusClientV2 = createConnection();
    }

    @AfterAll
    static void clean() throws InterruptedException {
        dropCollections("Books");
        milvusClientV2.close(2);
        logger.info("connection closed successfully");
    }

    private static MilvusClientV2 createConnection() {
        ConnectConfig connectConfig = ConnectConfig.builder()
            .uri(CONNECTION_URI)
            .token(API_KEY)
            .build();
        milvusClientV2 = new MilvusClientV2(connectConfig);

        logger.info("connection created successfully, collections fetched: {}",
            milvusClientV2.listCollections().getCollectionNames()
        );
        return milvusClientV2;
    }

    private static void createCollections(String ... collectionNames) {
        for (String collectionName : collectionNames) {
            createCollection(collectionName);
            logger.info("Collection {} created", collectionName);
        }
    }

    private static void createCollection(String collectionName) {
        CreateCollectionReq createCollectionReq = CreateCollectionReq.builder()
            .collectionName(collectionName)
            .dimension(4)
            .build();
        milvusClientV2.createCollection(createCollectionReq);
    }

    private static void dropCollections(String... collectionNames) {
        for (String collectionName : collectionNames) {
            dropCollection(collectionName);
            logger.info("Collection {} dropped", collectionName);
        }
    }

    private static void dropCollection(String collectionName) {
        DropCollectionReq dropCollectionReq = DropCollectionReq.builder()
            .collectionName(collectionName)
            .build();
        milvusClientV2.dropCollection(dropCollectionReq);
    }

    private static CreateCollectionReq.FieldSchema createFieldSchema(String name, String desc, DataType dataType,
        boolean isPrimary, Integer dimension) {
        CreateCollectionReq.FieldSchema fieldSchema = CreateCollectionReq.FieldSchema.builder()
            .name(name)
            .description(desc)
            .autoID(false)
            .isPrimaryKey(isPrimary)
            .dataType(dataType)
            .build();
        if (null != dimension) {
            fieldSchema.setDimension(dimension);
        }
        return fieldSchema;
    }

    private static List<CreateCollectionReq.FieldSchema> createFieldSchemas() {
        List<CreateCollectionReq.FieldSchema> fieldSchemas = List.of(
            createFieldSchema("book_id", "Primary key", DataType.Int64, true, null),
            createFieldSchema("book_name", "Book Name", DataType.VarChar, false, null),
            createFieldSchema("book_vector", "vector field", DataType.FloatVector, false, 5)
        );
        return fieldSchemas;
    }

    private static IndexParam createIndexParam(String fieldName, String indexName) {
        return IndexParam.builder()
            .fieldName(fieldName)
            .indexName(indexName)
            .metricType(IndexParam.MetricType.COSINE)
            .indexType(IndexParam.IndexType.AUTOINDEX)
            .build();
    }

    private List<JSONObject> readJsonObjectsFromFile(String fileName) throws FileNotFoundException {
        String filePath = getClass().getClassLoader().getResource(fileName).getPath();
        List<JSONObject> bookJsons = JsonUtility.getJsonObjects(filePath).stream().map(e -> {
            List<BigDecimal> bookVectorDouble = e.getObject("book_vector", List.class);

            JSONObject book = new JSONObject();
            book.put("book_id", e.getLong("book_id"));
            book.put("book_name", e.getString("book_name"));
            book.put("book_vector", bookVectorDouble.stream()
                .map(el -> el.floatValue())
                .collect(Collectors.toList())
            );
            return book;
        }).collect(Collectors.toList());
        return bookJsons;
    }

    @Test
    @Order(1)
    void whenCommandCreateCollectionInVectorDB_thenSuccess() {
        List<CreateCollectionReq.FieldSchema> fieldSchemas = createFieldSchemas();

        CreateCollectionReq.CollectionSchema collectionSchemaForBooks = CreateCollectionReq.CollectionSchema.builder()
            .fieldSchemaList(fieldSchemas)
            .build();
        CreateCollectionReq createCollectionReq = CreateCollectionReq.builder()
            .collectionName("Books")
            .indexParams(List.of(createIndexParam("book_vector", "book_vector_indx")))
            .description("Collection for storing the details of books")
            .collectionSchema(collectionSchemaForBooks)
            .build();
        milvusClientV2.createCollection(createCollectionReq);
        assertTrue(milvusClientV2.hasCollection(HasCollectionReq.builder()
            .collectionName("Books")
            .build()));
    }

    @Test
    @Order(2)
    void whenCommandInsertDataIntoVectorDB_thenSuccess() throws IOException {

        List<JSONObject> bookJsons = readJsonObjectsFromFile("yoga_vectors.json");

        InsertReq insertReq = InsertReq.builder()
            .collectionName("Books")
            .data(bookJsons)
            .build();
        InsertResp insertResp = milvusClientV2.insert(insertReq);
        logger.info("No. of records inserted: {}", insertResp.getInsertCnt());
        assertEquals(bookJsons.size(), insertResp.getInsertCnt());
    }
    @Test
    @Order(3)
    void givenListOfIds_whenCommandDeleteDataFromCollection_thenSuccess() {
        DeleteReq deleteReq = DeleteReq.builder()
            .collectionName("Books")
            .ids(List.of(1, 2))
            .build();

        DeleteResp deleteResp = milvusClientV2.delete(deleteReq);
        assertEquals(2, deleteResp.getDeleteCnt());
    }

    @Test
    @Order(4)
    void givenFilterCondition_whenCommandDeleteDataFromCollection_thenSuccess() {
        DeleteReq deleteReq = DeleteReq.builder()
            .collectionName("Books")
            .filter("book_id > 4")
            .build();

        DeleteResp deleteResp = milvusClientV2.delete(deleteReq);
        assertTrue(deleteResp.getDeleteCnt() >= 1 );
    }
}
