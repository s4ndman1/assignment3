package za.ac.university.pretoria.node.mvc.controller;


import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.sql.*;

@Singleton
@Startup
public class DatabaseConnection {


//    public void creatTables(){
//
//        String createState = "CREATE TABLE STATE_MAHCINE\n" +
//                "(\n" +
//                "  state_id VARCHAR2(20 CHAR),\n" +
//                "  state_description VARCHAR2(20 CHAR) NOT NULL,\n" +
//                "  CONSTRAINT state_info_pk PRIMARY KEY(state_id)\n" +
//                ")";
//        String createNode = "CREATE TABLE NODE_INFO\n" +
//                "(\n" +
//                "  node_id VARCHAR2(20 CHAR),\n" +
//                "  admin_id VARCHAR2(20 CHAR),\n" +
//                "  node_creation_date DATE NOT NULL,\n" +
//                "  node_status VARCHAR2(20 CHAR) NOT NULL REFERENCES STATE_MAHCINE(state_id),\n" +
//                "  CONSTRAINT node_info_pk PRIMARY KEY(node_id)\n" +
//                ")";
//        String createCalendar = "CREATE TABLE NODE_CALENDER\n" +
//                "(\n" +
//                "  calender_id VARCHAR2(20 CHAR),\t\n" +
//                "  node_active_start_time VARCHAR2(20 CHAR) NOT NULL,\n" +
//                "  node_active_end_time VARCHAR2(20 CHAR) NOT NULL,\n" +
//                "  node_id_fk VARCHAR2(20 CHAR) NOT NULL REFERENCES NODE_INFO(node_id),\n" +
//                "  CONSTRAINT node_calender_pk PRIMARY KEY(calender_id)\n" +
//                ")";
//        String creatTasks = "CREATE TABLE NODE_TASKS\n" +
//                "(\n" +
//                "  task_id VARCHAR2(20 CHAR),\n" +
//                "  start_time DATE NOT NULL,\n" +
//                "  end_time DATE,\n" +
//                "  node_id_fk VARCHAR2(20 CHAR) NOT NULL REFERENCES NODE_INFO(node_id),\n" +
//                "  CONSTRAINT task_table_pk PRIMARY KEY(task_id)\n" +
//                ")";
//        String insertStateActive = "Insert into COS.STATE_MAHCINE (STATE_ID,STATE_DESCRIPTION) values ('1','Active')";
//        String insertStateBusy = "Insert into COS.STATE_MAHCINE (STATE_ID,STATE_DESCRIPTION) values ('2','Busy')";
//        String insertStateUnavailable = "Insert into COS.STATE_MAHCINE (STATE_ID,STATE_DESCRIPTION) values ('3','Unavailable')";
//
//        try {
//            stmt.execute(createState);
//        } catch (SQLException e) {
//            logger.error("There was a problem creating state machine");
//        }
//
//        try {
//            stmt.execute(createNode);
//        } catch (SQLException e) {
//            logger.error("There was a problem creating node");
//        }
//
//        try {
//            stmt.execute(createCalendar);
//        } catch (SQLException e) {
//            logger.error("There was a problem creating calendar");
//        }
//
//        try {
//            stmt.execute(creatTasks);
//        } catch (SQLException e) {
//            logger.error("There was a problem creating tasks");
//        }
//
//        try {
//            stmt.execute(insertStateActive);
//        } catch (SQLException e) {
//            logger.error("There was a problem inserting active");
//        }
//
//        try {
//            stmt.execute(insertStateBusy);
//        } catch (SQLException e) {
//            logger.error("There was a problem inserting busy");
//        }
//        try {
//            stmt.execute(insertStateUnavailable);
//        } catch (SQLException e) {
//            logger.error("There was a problem inserting unavailable");
//        }
//
//    }

    Logger logger = Logger.getLogger(DatabaseConnection.class);
    private DataSource dataSource;
    private Statement stmt;

    private Connection connect;

    public DatabaseConnection() throws SQLException, ClassNotFoundException {

        try {
            InitialContext ctx = new InitialContext();

            dataSource = (DataSource) ctx.lookup("jdbc/node");
        } catch (NamingException e) {
            logger.error("There was a problem finding the data source ", e);
        }
        connect = dataSource.getConnection();
        stmt = connect.createStatement();
        logger.info("Database connection successfully made");

    }

    public ResultSet executeQuery(String query) throws SQLException {
        synchronized (stmt) {
            ResultSet result = stmt.executeQuery(query);
            return result;
        }
    }

}
