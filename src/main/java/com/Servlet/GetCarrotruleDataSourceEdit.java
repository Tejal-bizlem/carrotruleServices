package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mongodb.GetDatasourcelist;

/**
 * Servlet implementation class GetCarrotruleDataSourceEdit
 */
@WebServlet("/GetCarrotruleDataSourceEdit")
public class GetCarrotruleDataSourceEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCarrotruleDataSourceEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // https://bluealgo.com:8088/CarrotRuleServices/GetCarrotruleDataSourceEdit?email=nilesh@gmail.com&group=G1&datasourcename=newdata
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out =response.getWriter();
		String usrid=request.getParameter("email");
 	   String group=request.getParameter("group");
 	   String datasourcename=request.getParameter("datasourcename");
 	   
		JSONObject resp=GetDatasourcelist.getDatasourceEdit(usrid, group, datasourcename,response);
		out.println(resp);

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
