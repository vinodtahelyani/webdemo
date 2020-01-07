package org.gradle.demo;

import com.mongodb.MongoClient;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "HelloServlet", urlPatterns = {"hello"}, loadOnStartup = 1)
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {





        StringBuffer message = new StringBuffer();


    try {

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleApp", "root", "admin");
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("select * from User");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (rs.next()) {
            message.append("" + rs.getInt(1) + ":" + rs.getString(2) + "");
        }
    }
    catch (Exception e){
        e.printStackTrace();
    }


        MongoClient mongoClient =  new MongoClient("localhost", 27017);


	        Iterable<String> it = mongoClient.listDatabaseNames();

	        Iterator i =  it.iterator();
	        while(i.hasNext()){
	            message.append(i.next());
	        }

        MongoDatabase database = mongoClient.getDatabase("todo-app");

        Iterable doc = database.getCollection("user").find();


        i = doc.iterator();

        while(i.hasNext()){
             message.append(i.next().toString() + "\n");
        }


        response.getWriter().print(message.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name == null) name = "World";
        request.setAttribute("user", name);
        request.getRequestDispatcher("response.jsp").forward(request, response);
    }
}