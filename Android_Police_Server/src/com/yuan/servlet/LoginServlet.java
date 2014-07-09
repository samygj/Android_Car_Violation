package com.yuan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username + ":" + password);
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String msg = null;
		boolean flag = false;
		
		 Connection con = null;    
		    Statement stmt = null;
		    ResultSet rs = null;
		    try {
		      Class.forName("com.mysql.jdbc.Driver");
		      con =DriverManager.getConnection ("jdbc:mysql://localhost:3306/android_police","root", "643313");
		      stmt = con.createStatement();
		      rs = stmt.executeQuery("SELECT * FROM user");
		      // displaying records
		      while(rs.next()){
		    	  String c1 = rs.getString(1);
		    	  String c2 = rs.getString(2);
			      if (c1.equals(username) && c2.equals(password)) {
			    	  msg = "success";
			    	  flag = true;
			    	  out.print(msg);
			      }
		      }
		      if (flag == false) {
		    	  msg = "fail";
		    	  out.print(msg);
		      }
		    } catch (SQLException e) {
		    	e.printStackTrace();
		      } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      out.close();
		     

		
		
//		if (username != null && username.equals("admin") && password != null
//				&& password.equals("1")) {
//			msg = "success";
//		} else {
//			msg = "fail";
//		}
//		System.out.println(msg);
//		out.print(msg);
//		out.flush();
//		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
