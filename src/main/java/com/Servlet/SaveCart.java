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
 * Servlet implementation class SaveCart
 */
@WebServlet("/SaveCart")
public class SaveCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveCart() {
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
// https://bluealgo.com:8088/CarrotRuleServices/SaveCart
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
		email = resultjsonobject.getString("owner_email").trim();
		
			JSONObject respjs=	Savedatasource.updateCartDetails(resultjsonobject.toString(), email);
			out.println(respjs);
	}

}
