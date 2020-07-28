package com.Servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.GetDatasourcelist;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class JsonForRules
 */
@WebServlet("/JsonForRules")
public class JsonForRules extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JsonForRules() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// https://bluealgo.com:8088/CarrotRuleServices/JsonForRules
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1) {
			buf.write((byte) result);
			result = bis.read();
		}
		String res = buf.toString("UTF-8");
		JSONObject obj = new JSONObject(res);
		// out.print(obj);

		JSONObject js = new JSONObject();
		String email = "";
		String group = "";
		String RuleName = "";
		String datasourcename = "";
		String ProjectName = "";
		String ruletype = "";
		String blank = "";

		JSONObject returnobj = new JSONObject(
				"{\"Mainjson\":[{\"RuleEngine\":\"\",\"Trigger\":[],\"Special Incentives RuleEngine\":\"\",\"Output\":[],\"Special_Incentives_Output\":[{\"Field\":\"\",\"Value\":\"\"}],\"Special Incentives\":[{\"Field\":\"Offer\",\"Type\":\"String\",\"Category\":\"\",\"Category1\":\"\",\"Value\":\"\"}]}],\"Level\":[\"Entry_level\"],\"project_name\":\"\",\"username\":\"\"}\r\n"
						+ "");
		out.println("returnobj= "+returnobj);
		JSONArray fieldarr = new JSONArray();
		JSONArray outputarr = null;

		email = obj.getString("email").trim();
		group = obj.getString("group").trim();
		datasourcename = obj.getString("datasourcename").trim();
		ProjectName = obj.getString("projectname").trim();
		ruletype = obj.getString("ruletype").trim();

		JSONObject Datasourcejs = GetDatasourcelist.getDatasourceEdit(email, group, datasourcename,response);
		out.println("Datasourcejs= "+Datasourcejs);
		JSONObject dtjs = Datasourcejs.getJSONArray(datasourcename).getJSONObject(1);
		out.println("1= "+dtjs);
		ArrayList<String> list = new ArrayList<String>();
		// out.println("DocTigerAdvance "+DocTigerAdvance);

		returnobj.put("project_name", ProjectName);
		returnobj.put("username", email);

		returnobj.put("datasourcename", datasourcename);
		returnobj.put("group", group);
		returnobj.put("source", "carrotrule");
		returnobj.put("ruletype", ruletype);
		returnobj.put("DataSource_Url", dtjs.getString("fieldExcel_url"));
		JSONArray arr1 = dtjs.getJSONArray("selectedfields");
		String[] arr = new String[arr1.length()];
		for (int i = 0; i < arr.length; i++) {
			JSONObject subobj = new JSONObject();
			/*
			 * subobj.put("Field", arr[i].getString()); subobj.put("Category", blank);
			 * subobj.put("Category1", blank); subobj.put("Value", blank);
			 * subobj.put("type", "String");
			 */
			if (arr[i].indexOf("/") != -1) {
				subobj.put("Field", arr[i].split("/")[0]);
				subobj.put("Category", blank);
				subobj.put("Category1", blank);
				subobj.put("Value", blank);
				subobj.put("type", arr[i].split("/")[1]);
			} else {
				subobj.put("Field", arr[i]);
				subobj.put("Category", blank);
				subobj.put("Category1", blank);
				subobj.put("Value", blank);
				subobj.put("type", "String");
			}
			list.add(arr[i]);
			fieldarr.put(subobj);
		}
		out.println("list= "+list);
		returnobj.put("DataSource_Fields", list);
		returnobj.getJSONArray("Mainjson").getJSONObject(0).put("Trigger", fieldarr);

		if (ruletype.equalsIgnoreCase("variable")) {

		} else if (ruletype.equalsIgnoreCase("clause")) {
		}

		out.println(returnobj);
	}

}
