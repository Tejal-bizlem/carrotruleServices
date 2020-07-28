package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import com.mongodb.DatasourceDelete;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteDatasource
 */
@WebServlet("/DeleteDatasource")
public class DeleteDatasource extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDatasource() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
       PrintWriter out=response.getWriter();
       String email=request.getParameter("email");
 	   String group=request.getParameter("group");
 	   String datasourcename=request.getParameter("datasourcename");
 	   String resp=DatasourceDelete.DeleteDatasource(email, group,datasourcename);
 	   out.print(resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
