package com.baeldung.dynamodb.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractRepository<T, ID extends Serializable> {

    protected DynamoDBMapper mapper;
    protected Class<T> entityClass;

    protected AbstractRepository() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();

        // This entityClass refers to the actual entity class in the subclass declaration.

        // For instance, ProductInfoDAO extends AbstractDAO<ProductInfo, String>
        // In this case entityClass = ProductInfo, and ID is String type
        // which refers to the ProductInfo's partition key string value
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public void save(T t) {
        mapper.save(t);
    }

    public T findOne(ID id) {
        return mapper.load(entityClass, id);
    }

    /**
     * <strong>WARNING:</strong> It is not recommended to perform full table scan
     * targeting the real production environment.
     *
     * @return All items
     */
    public List<T> findAll() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return mapper.scan(entityClass, scanExpression);
    }

    public void setMapper(DynamoDBMapper dynamoDBMapper) {
        this.mapper = dynamoDBMapper;
    }

}
