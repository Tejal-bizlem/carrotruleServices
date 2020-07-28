package com.mongodb;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class GetUserRoleId {
	
	
	
	public static JSONObject getRoleId(String Email,String group) {

		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		
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
//			out.println("filter= "+filter);
			String skutype="";
			
			while (monitordata.hasNext()) {

				try {
					datsrc_doc = (Document) monitordata.next();
				
					datasrcListJson = new JSONObject(datsrc_doc.toJson());
					skutype=datasrcListJson.getString("datasrcListJson");
					
					/// type

					break;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			String roleid="";

			if (datasrcListJson.has("Group")) {
				JSONArray datajs = datasrcListJson.getJSONArray("Group");
				for (int i = 0; i < datajs.length(); i++) {
			if(datajs.getJSONObject(i).getString("Groupname").equals(group)) {	
				JSONArray userarr=datajs.getJSONObject(i).getJSONArray("users");
				for(int k=0;k<userarr.length();k++) {
					if(userarr.getJSONObject(k).getString("Email").equals(Email)) {
						roleid=userarr.getJSONObject(k).getString("roleid");
						
					}
				}
				
			}
				}

			}else {
				
				skutype="All";
			}
			
			alldatasrcList.put("roleid", roleid);
			alldatasrcList.put("skutype", skutype);

		} catch (Exception ex) {
			System.out.println("Error : " + ex.getMessage());

		}
		return alldatasrcList;
	}

}
