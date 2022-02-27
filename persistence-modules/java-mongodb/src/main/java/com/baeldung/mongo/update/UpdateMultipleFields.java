package com.baeldung.mongo.update;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class UpdateMultipleFields {

	public static void main(String[] args) {

		//
	    // Connect to cluster
	    //

		MongoClient mongoClient = new MongoClient("localhost", 27007);
		MongoDatabase database = mongoClient.getDatabase("baeldung");
		MongoCollection<Document> collection = database.getCollection("employee");

		//
		// Update query
		//

		UpdateResult updateResult = collection.updateMany(Filters.eq("employee_id", 794875),
				Updates.combine(Updates.set("department_id", 4), Updates.set("job", "Sales Manager")));

		System.out.println("updateResult:- " + updateResult);
		System.out.println("updateResult:- " + updateResult.getModifiedCount());

	}

}