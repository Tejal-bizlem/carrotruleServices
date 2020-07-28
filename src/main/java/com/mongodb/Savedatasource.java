package com.mongodb;


import static com.mongodb.client.model.Filters.eq;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
public class Savedatasource {

	public static JSONObject updateDataSource(String xlsjson, String Email, String datasourcename, String group) {
		JSONObject respjs=new JSONObject();
	
		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		Bson document = null;
		try {
			//mongoClient = ConnectionHelper.getConnection();
			 database=MongoDbCache.getInstance().getConnection().getDatabase("carrotruledata");

			collection = database.getCollection("Datasource");
			document = Document.parse(xlsjson);

			Bson searchQuery = new Document().append("Email", Email).append("datasourcename", datasourcename)
					.append("group", group);
			collection.updateOne(searchQuery, new Document("$set", document), new UpdateOptions().upsert(true));
			respjs.put("status", "success");

		} catch (Exception e) {
			e.printStackTrace();
			respjs.put("error", e.toString());
		} finally {
			collection=null;
			database=null;

		}
		return respjs;
	}
	public static JSONObject updateCartDetails(String xlsjson, String Email) {
		JSONObject respjs=new JSONObject();
	
		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		Bson document = null;
		try {
			//mongoClient = ConnectionHelper.getConnection();
			 database=MongoDbCache.getInstance().getConnection().getDatabase("carrotruledata");

			collection = database.getCollection("FreetrialAndCart");
			document = Document.parse(xlsjson);

			Bson searchQuery = new Document().append("owner_email", Email);
			collection.updateOne(searchQuery, new Document("$set", document), new UpdateOptions().upsert(true));
			respjs.put("status", "success");

		} catch (Exception e) {
			e.printStackTrace();
			respjs.put("error", e.toString());
		} finally {
			collection=null;
			database=null;

		}
		return respjs;
	}

}
