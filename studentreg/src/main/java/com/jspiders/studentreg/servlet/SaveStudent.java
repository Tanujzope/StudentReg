package com.jspiders.studentreg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SaveStudent")
public class SaveStudent extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static int result;
	private static String driverPath = "com.mysql.cj.jdbc.Driver";
	private static String dburl = "jdbc:mysql://localhost:3306/servlet";
	private static String user = "root";
	private static String password= "root";
	private static String query;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String address = req.getParameter("address");
		
		PrintWriter writer = resp.getWriter();
		
		
		
		try {
			Class.forName(driverPath);
			connection = DriverManager.getConnection(dburl, user, password);
			query = "insert into student_data values(?, ? ,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, address);
			
			result = preparedStatement.executeUpdate();
			resp.setContentType("text/html");
			if(result != 0) {
				writer.println("<h1>Student Data Added Successfully</h1>");
			}
			
			if(connection!= null)
				connection.close();
			
			if(preparedStatement != null)
				preparedStatement.close();
		}catch (SQLIntegrityConstraintViolationException e) {
			// TODO: handle exception
			writer.println("<h1 style='color:red';>ID Already Exist. Please Try New One</h1>");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
