package com.mongodb;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class DatasourceDelete {

	public static String DeleteDatasource(String Email, String group,String datasourcename) {
		// MongoClient mongoClient = null;
		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		
		try {
			database = MongoDbCache.getInstance().getConnection().getDatabase("carrotruledata");
			collection = database.getCollection("Datasource");
			Bson filter = and(eq("Email", Email), eq("group", group),eq("datasourcename", datasourcename));
			
			collection.deleteMany(filter);

		} catch (Exception ex) {
			System.out.println("Error : " + ex.getMessage());

		}
		return "success";
	}
}
