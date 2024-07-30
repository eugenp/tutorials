package com.baeldung.milvus;

import static com.baeldung.utility.PropertyUtil.getVal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
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
import com.baeldung.utility.JsonUtil;

import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.common.DataType;
import io.milvus.v2.common.IndexParam;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import io.milvus.v2.service.collection.request.DropCollectionReq;
import io.milvus.v2.service.collection.request.HasCollectionReq;
import io.milvus.v2.service.partition.request.CreatePartitionReq;
import io.milvus.v2.service.partition.request.HasPartitionReq;
import io.milvus.v2.service.vector.request.DeleteReq;
import io.milvus.v2.service.vector.request.InsertReq;
import io.milvus.v2.service.vector.request.SearchReq;
import io.milvus.v2.service.vector.response.DeleteResp;
import io.milvus.v2.service.vector.response.InsertResp;
import io.milvus.v2.service.vector.response.SearchResp;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MilvusVectorDBLiveTest {
    private static final Logger logger = LoggerFactory.getLogger(MilvusVectorDBLiveTest.class);

    private static final String PROPERTY_FILE = "milvus.properties";
    private static final String CONNECTION_URI = getVal("CONNECTION_URI", PROPERTY_FILE);
    private static final String API_KEY = getVal("API_TOKEN", PROPERTY_FILE);

    private static MilvusClientV2 milvusClientV2 = null;

    @BeforeAll
    static void setup() {
        milvusClientV2 = createConnection();
    }

    @AfterAll
    static void clean() throws InterruptedException {
        dropCollection("Books");
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

    private static CreateCollectionReq.CollectionSchema createCollectionSchema(
        List<CreateCollectionReq.FieldSchema> fieldSchemas) {
        return CreateCollectionReq.CollectionSchema.builder()
            .fieldSchemaList(fieldSchemas)
            .build();
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
        List<JSONObject> bookJsons = JsonUtil.getJsonObjects(filePath).stream().map(e -> {
            List<BigDecimal> bookVectorDouble = e.getObject("book_vector", List.class);

            JSONObject book = new JSONObject();
            book.put("book_id", e.getLong("book_id"));
            book.put("book_name", e.getString("book_name"));
            book.put("book_vector", bookVectorDouble.stream()
                .map(BigDecimal::floatValue)
                .collect(Collectors.toList())
            );
            return book;
        }).collect(Collectors.toList());
        return bookJsons;
    }

    private static List<Float> getQueryEmbedding(String query) {
        //take the query String and generate the embedding
        return List.of(0.8883106956939386f,
            0.34840710312979917f,
            0.08596867125476038f,
            0.8619797519039474f,
            0.4589036272047099f);
    }

    @Test
    @Order(1)
    void whenCommandCreateCollectionInVectorDB_thenSuccess() {
        CreateCollectionReq createCollectionReq = CreateCollectionReq.builder()
            .collectionName("Books")
            .indexParams(List.of(createIndexParam("book_vector", "book_vector_indx")))
            .description("Collection for storing the details of books")
            .collectionSchema(createCollectionSchema(createFieldSchemas()))
            .build();
        milvusClientV2.createCollection(createCollectionReq);
        assertTrue(milvusClientV2.hasCollection(HasCollectionReq.builder()
            .collectionName("Books")
            .build()));
        logger.info("Collection Created Successfully!");
    }

    @Test
    @Order(2)
    void whenCommandCreatePartitionInCollection_thenSuccess() {
        CreatePartitionReq createPartitionReq = CreatePartitionReq.builder()
            .collectionName("Books")
            .partitionName("Health")
            .build();
        milvusClientV2.createPartition(createPartitionReq);

        assertTrue(milvusClientV2.hasPartition(HasPartitionReq.builder()
            .collectionName("Books")
            .partitionName("Health")
            .build()));
        logger.info("Partition Created Successfully!");
    }

    @Test
    @Order(3)
    void whenCommandInsertDataIntoVectorDB_thenSuccess() throws IOException {

        List<JSONObject> bookJsons = readJsonObjectsFromFile("book_vectors.json");
        logger.info("Data for insertion: {}", bookJsons);
        InsertReq insertReq = InsertReq.builder()
            .collectionName("Books")
            .partitionName("Health")
            .data(bookJsons)
            .build();
        InsertResp insertResp = milvusClientV2.insert(insertReq);
        logger.info("No. of records inserted: {}", insertResp.getInsertCnt());
        assertEquals(bookJsons.size(), insertResp.getInsertCnt());
    }

    @Test
    @Order(4)
    void givenSearchVector_whenCommandSearchDataFromCollection_thenSuccess() {
        List<Float> queryEmbedding = getQueryEmbedding("What are the benefits of Yoga?");
        SearchReq searchReq = SearchReq.builder()
            .collectionName("Books")
            .data(Collections.singletonList(queryEmbedding))
            .outputFields(List.of("book_id", "book_name"))
            .topK(2)
            .build();

        SearchResp searchResp = milvusClientV2.search(searchReq);
        List<List<SearchResp.SearchResult>> searchResults = searchResp.getSearchResults();
        searchResults.forEach(e -> e.forEach(el -> logger.info("book_id: {}, book_name: {}, distance: {}",
            el.getEntity().get("book_id"), el.getEntity().get("book_name"), el.getDistance()))
        );
    }

    @Test
    @Order(5)
    void givenListOfIds_whenCommandDeleteDataFromCollection_thenSuccess() {
        DeleteReq deleteReq = DeleteReq.builder()
            .collectionName("Books")
            .ids(List.of(1, 2))
            .build();

        DeleteResp deleteResp = milvusClientV2.delete(deleteReq);
        assertEquals(2, deleteResp.getDeleteCnt());
        logger.info("number of records deleted: {}", deleteResp.getDeleteCnt());
    }

    @Test
    @Order(6)
    void givenFilterCondition_whenCommandDeleteDataFromCollection_thenSuccess() {
        DeleteReq deleteReq = DeleteReq.builder()
            .collectionName("Books")
            .filter("book_id > 4")
            .build();

        DeleteResp deleteResp = milvusClientV2.delete(deleteReq);
        assertTrue(deleteResp.getDeleteCnt() >= 1 );
        logger.info("number of records deleted: {}", deleteResp.getDeleteCnt());
    }
}
