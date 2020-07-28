package com.mongodb;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class GetServiceId {

	public static JSONObject getServiceId(String Email) {

		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		MongoCursor<Document> DetailsCursor = null;
		JSONObject datasrcListJson = null;
		JSONObject dataJson = null;
		JSONObject alldatasrcList = new JSONObject();

		JSONArray dataarr = new JSONArray();

		try {
			database = MongoDbCache.getInstance().getConnection().getDatabase("carrotruledata");
			collection = database.getCollection("FreetrialAndCart");
			Bson filter = and(eq("Email", Email));
			FindIterable<Document> filerdata = collection.find(filter);
			MongoCursor<Document> monitordata = null;
			monitordata = filerdata.iterator();
			
			Document datsrc_doc=null;
			while (monitordata.hasNext()) {

				try {
			
				
							datsrc_doc = (Document) monitordata.next();
							
							datasrcListJson = new JSONObject(datsrc_doc.toJson());
//					datasrcListJson = new JSONObject(DetailsCursor.next().toString());
					dataJson = new JSONObject();
					dataJson.put("serviceid", datasrcListJson.getString("serviceId"));
					dataJson.put("user", datasrcListJson.getString("owner_email"));
					dataJson.put("owner", datasrcListJson.getString("owner_email"));
					dataJson.put("type", "cart");
					if (datasrcListJson.getString("role").equals("setup")) {
						dataJson.put("admin", "1");
					} else {
						dataJson.put("admin", datasrcListJson.getString("role"));
					}
					/// type

					break;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			alldatasrcList.put("success", "success");

		} catch (Exception ex) {
			System.out.println("Error : " + ex.getMessage());

		}
		return alldatasrcList;
	}

	public static JSONArray getGroups(String Email) {

		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		MongoCursor<Document> DetailsCursor = null;
		JSONObject datasrcListJson = null;
	    JSONArray grouparr = new JSONArray();

		try {
			database = MongoDbCache.getInstance().getConnection().getDatabase("carrotruledata");
			collection = database.getCollection("FreetrialAndCart");
			Bson filter = and(eq("Email", Email));
			FindIterable<Document> filerdata = collection.find(filter);
			MongoCursor<Document> monitordata = null;
			monitordata = filerdata.iterator();
			
			Document datsrc_doc=null;

			while (monitordata.hasNext()) {
				try {
					datsrc_doc = (Document) monitordata.next();
					
					datasrcListJson = new JSONObject(datsrc_doc.toJson());
					datasrcListJson = new JSONObject(DetailsCursor.next().toString());

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			if (datasrcListJson.has("Group")) {
				JSONArray datajs = datasrcListJson.getJSONArray("Group");
				for (int i = 0; i < datajs.length(); i++) {
					String group = datajs.getJSONObject(i).getString("Groupname");
					grouparr.put(group);
				}

			}

		} catch (Exception ex) {
			System.out.println("Error : " + ex.getMessage());

		}
		return grouparr;
	}

}
