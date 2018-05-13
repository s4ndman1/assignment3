package za.ac.university.pretoria.node.unit.Controller;

import java.sql.*;

public  class DatabaseConnection {
	
	static Connection connect = null;;
	static Statement stmt = null;
	
	 public DatabaseConnection() {
		 
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connect=DriverManager.getConnection(
                    "jdbc:oracle:thin:QuantumUP	/sys@//localhost:1521/orcl","sys as sysdba","1234");
			
			stmt = connect.createStatement();
			System.out.println("Database connected");
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		 
		 
	 }
	 
	 public boolean executeQuery(String query) throws SQLException  {
		 stmt.executeQuery(query);
		 return true;
	 }


	

}
