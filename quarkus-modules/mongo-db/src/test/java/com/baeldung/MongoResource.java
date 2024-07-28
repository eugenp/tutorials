package com.baeldung;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MongoDBContainer;

public class MongoResource implements QuarkusTestResourceLifecycleManager {

	private static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer("mongo:4.2");

	@Override
	public Map<String, String> start() {
		MONGO_DB_CONTAINER.start();
		Map<String, String> config = new HashMap<>();
		config.put("quarkus.mongodb.connection-string", MONGO_DB_CONTAINER.getReplicaSetUrl());
		return config;
	}

	@Override
	public void stop() {
		MONGO_DB_CONTAINER.stop();
	}
}
