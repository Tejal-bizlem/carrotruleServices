package com.mongodb;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.FindIterable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

public class UpdateExcelRecProperty {

	public static JSONObject updateDataSource(String excelrecords, String Email, String group) {
		JSONObject respjs = new JSONObject();

		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		MongoCursor<Document> DetailsCursor = null;
		Bson document = null;
		JSONObject datasrcListJson = null;
		String freetrial = "false";
		String quantity = "";
		String ExcelRecords_count = "0";
		String end_date = "";
		boolean validity = false;
		String status = "false";
		String sku = "";

		try {
			// mongoClient = ConnectionHelper.getConnection();
			database = MongoDbCache.getInstance().getConnection().getDatabase("carrotruledata");

			collection = database.getCollection("FreetrialAndCart");
			JSONObject js = new JSONObject();

			Bson freetrialfilter = and(eq("owner_email", Email), eq("serviceId", "Freetrial"));
			DetailsCursor = collection.find(freetrialfilter).iterator();

			while (DetailsCursor.hasNext()) {
				freetrial = "true";
				try {

					validity = true;
					end_date = datasrcListJson.getString("end_date");
					String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					Date currentdateobj = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
					Date enddateobj = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
					sku = datasrcListJson.getString("skutype");

					JSONObject json = new JSONObject();
					status = "true";

					if (enddateobj.before(currentdateobj)) {
						validity = false;
						status = "false";

					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			if (freetrial.equals("false")) {
				DetailsCursor = null;
				Bson filter = and(eq("owner_email", Email), eq("producttype", "carrotrule"));
				FindIterable<Document> filerdata = collection.find(filter);
				MongoCursor<Document> monitordata = null;
				monitordata = filerdata.iterator();
				DetailsCursor = collection.find(filter).iterator();
				Document datsrc_doc=null;

				while (monitordata.hasNext()) {

					try {
						datsrc_doc = (Document) monitordata.next();
						datsrc_doc.remove("_id");
						datasrcListJson = new JSONObject(datsrc_doc.toJson());
//						datasrcListJson = new JSONObject(DetailsCursor.next().toString());

						String excelrec = datasrcListJson.getString("ExcelRecords_count");
						quantity = datasrcListJson.getString("quantity");

						int qty = Integer.parseInt(quantity);
						int dcount = Integer.parseInt(excelrec);
						int recordcount = Integer.parseInt(excelrecords);
						sku = datasrcListJson.getString("skutype");
						dcount = dcount + recordcount;
						if (qty > dcount) {
							validity = true;
							end_date = datasrcListJson.getString("end_date");
							String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
							Date currentdateobj = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
							Date enddateobj = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);

							JSONObject json = new JSONObject();

							json.put("ExcelRecords_count", Integer.toString(dcount));
							document = Document.parse(json.toString());

							Bson searchQuery = new Document().append("owner_email", Email);
							collection.updateOne(searchQuery, new Document("$set", document),
									new UpdateOptions().upsert(true));
							status = "true";
						

							// datasrcListJson.getString("quantity")
							if (enddateobj.before(currentdateobj)) {
								validity = false;
								status = "false";
								
							}
						} else {
							validity = false;
						}

						// quantity

					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}
			
			if(Email.equalsIgnoreCase("cpquser@gmail.com") ){
				status="true";
				sku="All";
			}

			respjs.put("status",status);
			respjs.put("Sku", sku);
			

		} catch (Exception e) {
			e.printStackTrace();
			respjs.put("error", e.toString());
		} finally {
			collection = null;
			database = null;

		}
		return respjs;
	}
}
// {
// "serviceId":"Freetrial",
// "Document_count_Id":"89",
// "documentTrackingQuantity":"1",
// "end_date":"2020-10-21",
// "owner_email":"pallavi@bizlem.io",
// "producttype":"doctiger",
// "quantity":"1000",
// "skutype":"doctiger701",
// "start_date":"2019-10-21",
// "users":[{
// "Email":"cpquser@gmail.com",
// "Document_count_user":"89",
// "role":"setup",
// "roleid ":"10",
//
// }]
//
// }
//
//
// {
// "serviceId":"Freetrial",
// "Document_count_Id":"89",
// "documentTrackingQuantity":"1",
// "end_date":"2020-10-21",
// "owner_email":"pallavi@bizlem.io",
// "producttype":"doctiger",
// "quantity":"1000",
// "skutype":"doctiger701",
// "start_date":"2019-10-21",
// "Group":[{
// "Groupname":"G1",
//
// "Email":"cpquser@gmail.com",
// "Document_count_user":"89",
// "role":"setup",
// "roleid ":"10"
//
// }]
//
// }
// {
// "serviceId":"40",
// "ExcelRecords_count ":"89",
// "documentTrackingQuantity":"1",
// "end_date":"2020-10-21",
// "owner_email":"abhishek.bizlem@gmail.com",
// "producttype":"2020-05-09",
// "quantity":"10000",
// "skutype":"carrotrule101",
// "start_date":"2019-10-21",
// "Group":[{
// "Groupname":"G1",
// "users":[{
// "Email":"abhishek.bizlem@gmail.com",
// "role":"setup",
// "roleid ":"8"
// }]
// }]}
//
//
//
// }
