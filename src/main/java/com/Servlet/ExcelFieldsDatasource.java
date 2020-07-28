package com.Servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.service.ExcelFields;

/**
 * Servlet implementation class ExcelFieldsDatasource
 */
@WebServlet("/ExcelFieldsDatasource")
public class ExcelFieldsDatasource extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcelFieldsDatasource() {
        super();
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
		PrintWriter out=response.getWriter();
		BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();

		while (result != -1) {
			buf.write((byte) result);
			result = bis.read();
		}
		String res = buf.toString("UTF-8");
		JSONObject resultjsonobject = new JSONObject(res);
		//out.println("resultjsonobject  "+resultjsonobject);
		String email = "";
		String group="";
		
				
		email = resultjsonobject.getString("Email").trim();
		group = resultjsonobject.getString("group").trim();
		String filedata = resultjsonobject.getString("filedata").trim();
		String filename = resultjsonobject.getString("filename").trim();
		
	    JSONArray arr = 	new ExcelFields().ReadFieldrow(filename, filedata);
	   out.println(arr);
	   /* JSONObject filejs=null;
		
		filejs=	new CreateExcel().createExcel(arr, datasourcename, email);
		 resultjsonobject.put("fieldExcel_url", filejs.getString("fileurl"));
		 resultjsonobject.put("fileName", filejs.getString("filename"));
	
			JSONObject respjs=	Savedatasource.updateDataSource(resultjsonobject.toString(), email, datasourcename, group);
			out.println(respjs);
	 */
	    	
	
	}

}
