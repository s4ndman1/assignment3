package za.ac.university.pretoria.node.mvc.controller;

import java.sql.*;

public  class DatabaseConnection {
	
	private Connection connect;
	private Statement stmt;
	
	 public DatabaseConnection() throws SQLException, ClassNotFoundException {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			connect=DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:assignment","cos","password");
			
			stmt = connect.createStatement();
			System.out.println("Database connected");
		 
	 }

	 public ResultSet executeQuery(String query) throws SQLException  {
		 ResultSet result = stmt.executeQuery(query);
//		 List<String> results = new ArrayList<>();
//		 while (result.next()){
//			 results.add(result.getString(1));
//		 }
		 return result;
	 }


	

}
