package za.ac.university.pretoria.node.mvc.controller;


import org.apache.log4j.Logger;

import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.sql.*;

@Singleton
public class DatabaseConnection {

    Logger logger = Logger.getLogger(DatabaseConnection.class);
    private DataSource dataSource;

    private Statement stmt;
    private Connection connect;
    public DatabaseConnection() throws SQLException, ClassNotFoundException {

        try {
            InitialContext ctx = new InitialContext();

            dataSource = (DataSource) ctx.lookup("jdbc/quantum");
        } catch (NamingException e) {
           logger.error("There was a problem finding the data source ",e);
        }
        connect = dataSource.getConnection();
        stmt = connect.createStatement();
        System.out.println("Database connected");

    }

    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet result = stmt.executeQuery(query);
        return result;
    }


}
