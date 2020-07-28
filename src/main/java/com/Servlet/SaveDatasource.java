package com.Servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import com.mongodb.Savedatasource;
import com.service.CreateExcel;


/**
 * Servlet implementation class SaveDatasource
 */
@WebServlet("/SaveDatasource")
public class SaveDatasource extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SaveDatasource() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		PrintWriter out =response.getWriter();
		BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();

		while (result != -1) {
			buf.write((byte) result);
			result = bis.read();
		}
		String res = buf.toString("UTF-8");
		JSONObject resultjsonobject = new JSONObject(res);
		String datasourcename = "";
		String email = "";
		String SFEmail = "";
		String group = "";
		email = resultjsonobject.getString("Email").trim();
		group = resultjsonobject.getString("group").trim();
		datasourcename= resultjsonobject.getString("datasource");
		JSONArray	selectedfields = resultjsonobject.getJSONArray("selectedfields");
		
		JSONObject filejs=null;
		
		filejs=	new CreateExcel().createExcel(selectedfields, datasourcename, email);
		 resultjsonobject.put("fieldExcel_url", filejs.getString("fileurl"));
		 resultjsonobject.put("fileName", filejs.getString("filename"));
		 Date d=new Date();
		String Created_Date =d.toString();
		 resultjsonobject.put("Created_Date",Created_Date);
			JSONObject respjs=	Savedatasource.updateDataSource(resultjsonobject.toString(), email, datasourcename, group);
			out.println(respjs);
		
//		createExcel
		
		
		
	}

}
