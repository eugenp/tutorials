package com.baeldung.connectiondetails.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaultAdapter {
    private static final Logger logger = LoggerFactory.getLogger(VaultAdapter.class);
    public static String getSecret(String secretKey) {
        logger.info("call vault to get the secret of key: " + secretKey);

        //Postgres keys
        if (secretKey.equalsIgnoreCase("postgres_secret_key")) {
            return "postgres";
        }
        if (secretKey.equalsIgnoreCase("postgres_user_key")) {
            return "postgres";
        }
        if (secretKey.equalsIgnoreCase("postgres_jdbc_url")) {
            return "jdbc:postgresql://localhost:15432/postgresdb";
        }
        //RabbitMQ Server Keys
        if (secretKey.equalsIgnoreCase("rabbitmq_username")) {
            return "rabbitmquser";
        }
        if (secretKey.equalsIgnoreCase("rabbitmq_password")) {
            return "rabbitmq";
        }
        if (secretKey.equalsIgnoreCase("rabbitmq_port")) {
            return "5672";
        }
        if (secretKey.equalsIgnoreCase("rabbitmq_host")) {
            return "localhost";
        }
        //Redis Server Keys
        if (secretKey.equalsIgnoreCase("redis_username")) {
            return null;
        }
        if (secretKey.equalsIgnoreCase("redis_password")) {
            return "redis";
        }
        if (secretKey.equalsIgnoreCase("redis_port")) {
            return "6379";
        }
        if (secretKey.equalsIgnoreCase("redis_host")) {
            return "localhost";
        }
        //Mongo DB Keys
        if (secretKey.equalsIgnoreCase("mongo_connection_string")) {
            return "mongodb://localhost:27017/demodb";
        }

        //r2dbc Keys
        if (secretKey.equalsIgnoreCase("r2dbc_postgres_user")) {
            return "postgres";
        }
        if (secretKey.equalsIgnoreCase("r2dbc_postgres_secret")) {
            return "postgres";
        }
        if (secretKey.equalsIgnoreCase("r2dbc_postgres_host")) {
            return "localhost";
        }
        if (secretKey.equalsIgnoreCase("r2dbc_postgres_port")) {
            return "15432";
        }
        if (secretKey.equalsIgnoreCase("r2dbc_postgres_database")) {
            return "postgresdb";
        }
        //Elastic Search Keys
        if (secretKey.equalsIgnoreCase("elastic_user")) {
            return "elastic";
        }
        if (secretKey.equalsIgnoreCase("elastic_secret")) {
            return "secret";
        }
        if (secretKey.equalsIgnoreCase("elastic_host")) {
            return "localhost";
        }
        if (secretKey.equalsIgnoreCase("elastic_port1")) {
            return "19200";
        }
        if (secretKey.equalsIgnoreCase("elastic_port2")) {
            return "19300";
        }
        //Cassandra keys
        if (secretKey.equalsIgnoreCase("cassandra_user")) {
            return "cassandra";
        }
        if (secretKey.equalsIgnoreCase("cassandra_secret")) {
            return "secret";
        }
        if (secretKey.equalsIgnoreCase("cassandra_host")) {
            return "localhost";
        }
        if (secretKey.equalsIgnoreCase("cassandra_port")) {
            return "19042";
        }
        //Neo4j Keys
        if (secretKey.equalsIgnoreCase("neo4j_secret")) {
            return "neo4j123";
        }
        if (secretKey.equalsIgnoreCase("neo4j_uri")) {
            return "bolt://localhost:17687";
        }
        //Kafka Keys
        if (secretKey.equalsIgnoreCase("kafka_servers")) {
            return "localhost:19092";
        }
        //Couchbase Keys
        if(secretKey.equalsIgnoreCase("couch_user")) {
            return "Administrator";
        }
        if(secretKey.equalsIgnoreCase("couch_secret")) {
            return "password";
        }
        if(secretKey.equalsIgnoreCase("couch_connection_string")) {
            return "couchbase://127.0.0.1:8092";
        }
        //Zipkin Keys
        if(secretKey.equalsIgnoreCase("zipkin_span_endpoint")) {
            return "http://localhost:9411/api/v2/spans";
        }
        return "secretVal";
    }
}
