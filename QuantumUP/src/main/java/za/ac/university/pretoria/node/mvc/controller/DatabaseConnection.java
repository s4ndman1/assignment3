package za.ac.university.pretoria.node.mvc.controller;


import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.sql.DataSource;
import java.sql.*;

@Singleton
public  class DatabaseConnection {

    @Resource
	private DataSource dataSource;

	private Statement stmt;
	
	 public DatabaseConnection() throws SQLException, ClassNotFoundException {

			Connection connect=dataSource.getConnection();
			stmt = connect.createStatement();
			System.out.println("Database connected");
		 
	 }

	 public ResultSet executeQuery(String query) throws SQLException  {
		 ResultSet result = stmt.executeQuery(query);
		 return result;
	 }


	

}
