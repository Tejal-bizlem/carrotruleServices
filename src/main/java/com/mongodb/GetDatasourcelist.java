package com.mongodb;

import org.bson.Document;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class GetDatasourcelist {

	public static JSONObject getDatasourcedetails(String Email, String group,HttpServletResponse response) throws IOException {
		PrintWriter out =response.getWriter();
		// MongoClient mongoClient = null;
		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		MongoCursor<Document> DetailsCursor = null;
		JSONObject datasrcListJson = null;
		JSONObject dataJson = null;
		JSONObject alldatasrcList = new JSONObject();
//		out.println("getDatasourcedetails= ");
		JSONArray dataarr = new JSONArray();

		try {
			database = MongoDbCache.getInstance().getConnection().getDatabase("carrotruledata");
			collection = database.getCollection("Datasource");
			Bson filter = and(eq("Email", Email), eq("group", group));
			FindIterable<Document> filerdata = collection.find(filter);
			MongoCursor<Document> monitordata = null;
			monitordata = filerdata.iterator();
			
			Document datsrc_doc=null;
//			out.println("filter= "+filter);
			
			while (monitordata.hasNext()) {
			
				try {
					datsrc_doc = (Document) monitordata.next();
					datsrc_doc.remove("_id");
					out.println("filter= "+datsrc_doc.toJson());
					datasrcListJson = new JSONObject(datsrc_doc.toJson());
					out.println("datasrcListJson= "+datasrcListJson);
					dataJson = new JSONObject();
					dataJson.put("datasourcename", datasrcListJson.getString("datasourcename"));
					dataJson.put("Created_Date", datasrcListJson.getString("Created_Date"));
					dataJson.put("Created_By", datasrcListJson.getString("Email"));
					dataJson.put("Approved_By", "admin");
					dataJson.put("Flag", "1");
					dataJson.put("version", "0.1");
					dataJson.put("ExcelUrl", datasrcListJson.getString("fieldExcel_url"));
					dataJson.put("description", "");
					dataarr.put(dataJson);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			alldatasrcList.put("status", "success");
			alldatasrcList.put("datasorceList", dataarr);
		} catch (Exception ex) {
//			System.out.println("Error : " + ex.getMessage());
			ex.printStackTrace();

		}
		return alldatasrcList;
	}

	public static JSONObject getDatasourceEdit(String Email, String group, String datasourcename,HttpServletResponse response) throws IOException {
		// MongoClient mongoClient = null;
//		PrintWriter out =response.getWriter();
		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		MongoCursor<Document> DetailsCursor = null;
		JSONObject datasrcListJson = null;
		JSONObject dataJson = null;
		JSONObject alldatasrcList = new JSONObject();
		
		JSONArray dataarr = new JSONArray();

		try {
			database = MongoDbCache.getInstance().getConnection().getDatabase("carrotruledata");
			collection = database.getCollection("Datasource");
			Bson filter = and(eq("Email", Email), eq("group", group), eq("datasourcename", datasourcename));
			FindIterable<Document> filerdata = collection.find(filter);
			MongoCursor<Document> monitordata = null;
			monitordata = filerdata.iterator();
			DetailsCursor = collection.find(filter).iterator();
			Document datsrc_doc=null;
//			out.println("filter= "+filter);
			while (monitordata.hasNext()) {
				
				try {
					datsrc_doc = (Document) monitordata.next();
//					out.println("datsrc_doc= "+datsrc_doc);
					datsrc_doc.remove("_id");
//					out.println("datsrc_doc.toJson()= "+datsrc_doc.toJson());
										datasrcListJson = new JSONObject(datsrc_doc.toJson());
//										out.println("datasrcListJson= "+datasrcListJson);
//					datasrcListJson = new JSONObject(DetailsCursor.next().toString());
					dataJson = new JSONObject();
					dataJson.put("datasource", datasrcListJson.getString("datasource"));
					dataJson.put("selectedfields", datasrcListJson.getJSONArray("selectedfields"));
					dataJson.put("fieldExcel", datasrcListJson.getString("fileName"));
					dataJson.put("fieldExcel_url", datasrcListJson.getString("fieldExcel_url"));

					dataarr.put(dataJson);
					break;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			alldatasrcList.put("success", "success");
			alldatasrcList.put(datasourcename, dataarr);
		} catch (Exception ex) {
			System.out.println("Error : " + ex.getMessage());

		}
		return alldatasrcList;
	}

}
