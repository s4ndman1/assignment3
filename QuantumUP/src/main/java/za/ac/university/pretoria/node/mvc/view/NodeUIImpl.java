package za.ac.university.pretoria.node.mvc.view;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import za.ac.university.pretoria.node.api.NodeUI;
import za.ac.university.pretoria.node.mvc.controller.DatabaseConnection;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NodeUIImpl implements NodeUI {
	
	private DatabaseConnection con;
	private Random random;
	@Inject
	public NodeUIImpl(DatabaseConnection connection) throws SQLException, ClassNotFoundException {
		con=connection;
		random = new Random();
	}

	@Override
	public List<NodeInfo> viewAllNodes() throws SQLException{
		String query = "SELECT ni.ADMIN_ID, ni.NODE_ID, ni.NODE_CREATION_DATE, nc.NODE_ACTIVE_START_TIME, nc.NODE_ACTIVE_END_TIME, st.STATE_DESCRIPTION " +
				"FROM NODE_INFO ni " +
				"INNER JOIN NODE_CALENDER nc ON ni.NODE_ID = nc.NODE_ID_FK " +
				"INNER JOIN STATE_MAHCINE st ON ni.NODE_STATUS = st.STATE_ID ";
		
		ResultSet resultSet = con.executeQuery(query);
		List<NodeInfo> nodeInfos = new ArrayList<>();
		while (resultSet.next()){
			NodeInfo nodeInfo = new NodeInfo();
			nodeInfo.setAdminId(resultSet.getString(1));
			nodeInfo.setNodeId(resultSet.getString(2));

			LocalDate creationDate = resultSet.getDate(3).toLocalDate();;
			nodeInfo.setCreationDate(creationDate);

			LocalTime activeTime = LocalTime.parse(resultSet.getString(4));
			nodeInfo.setActiveTime(activeTime);

			LocalTime endTime = LocalTime.parse(resultSet.getString(5));
			nodeInfo.setEndTime(endTime);

			nodeInfo.setState(resultSet.getString(6));
			nodeInfos.add(nodeInfo);
		}

		return nodeInfos;
		
	}

	@Override
	public boolean killNode(String nodeID) throws SQLException {
		
		String query = "UPDATE NODE_INFO "
				+ "SET NODE_STATUS = "
				+ "(SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Unavailable')"
				+ " where NODE_ID = '" + nodeID+"'";
		
	
			con.executeQuery(query);
		
		return true;
	}

	private String createID(String id){

		LocalDateTime localDateTime = LocalDateTime.now();
		String date = "" + localDateTime.getYear() + localDateTime.getMonthValue() + localDateTime.getDayOfMonth() + localDateTime.getHour() + localDateTime.getMinute() + localDateTime.getSecond();
		int randomNr = random.nextInt(90) + 10;
		String identity = id + date + randomNr;
		return identity;
	}

	@Override
	public String approveNode(String adminID, LocalTime startTime,LocalTime endTime)  throws SQLException {
		try {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MMM/uuuu");

			String nodeID = createID("NOD");
			String query = "INSERT INTO NODE_INFO(node_id,admin_id,node_creation_date,node_status) VALUES('" + nodeID + "','" + adminID + "','" + LocalDate.now().format(df) + "','3') ";
			con.executeQuery(query);

			String calID = createID("CAL");
			String querycalender = "INSERT INTO NODE_CALENDER(calender_id,node_active_start_time, node_active_end_time,node_id_fk) VALUES('" + calID + "','" + startTime.toString() + "','" + endTime.toString() + "', '" + nodeID + "')";
			con.executeQuery(querycalender);
			return nodeID;
		}catch (SQLException e){
			if(e.getErrorCode() == 1)
				return approveNode(adminID,startTime,endTime);
			else
				throw e;
		}
	}

	@Override
	public boolean removeNode(String nodeID) throws SQLException {


		String query="DELETE FROM NODE_CALENDER WHERE NODE_ID_FK = '"+nodeID+"'";
		con.executeQuery(query);

		query="DELETE FROM NODE_INFO WHERE NODE_ID = '"+nodeID+"'";
		con.executeQuery(query);
		
		return true;
	}

	@Override
	public List<NodeInfo> viewAllOwnNodes(String adminID) throws SQLException {
		String query = "SELECT ni.ADMIN_ID, ni.NODE_ID, ni.NODE_CREATION_DATE, nc.NODE_ACTIVE_START_TIME, nc.NODE_ACTIVE_END_TIME, st.STATE_DESCRIPTION " +
				"FROM NODE_INFO ni " +
				"INNER JOIN NODE_CALENDER nc ON ni.NODE_ID = nc.NODE_ID_FK " +
				"INNER JOIN STATE_MAHCINE st ON ni.NODE_STATUS = st.STATE_ID " +
				"WHERE ni.ADMIN_ID = '"+adminID+"'";

		ResultSet resultSet = con.executeQuery(query);
		List<NodeInfo> nodeInfos = new ArrayList<>();
		while (resultSet.next()){
			NodeInfo nodeInfo = new NodeInfo();
			nodeInfo.setAdminId(resultSet.getString(1));
			nodeInfo.setNodeId(resultSet.getString(2));

			LocalDate creationDate = resultSet.getDate(3).toLocalDate();;
			nodeInfo.setCreationDate(creationDate);

			LocalTime activeTime = LocalTime.parse(resultSet.getString(4));
			nodeInfo.setActiveTime(activeTime);

			LocalTime endTime = LocalTime.parse(resultSet.getString(5));
			nodeInfo.setEndTime(endTime);

			nodeInfo.setState(resultSet.getString(6));
			nodeInfos.add(nodeInfo);
		}

		return nodeInfos;
	}

	@Override
	public boolean changeNodeToActive(String nodeID) throws SQLException {
		String query = "UPDATE NODE_INFO "
				+ "SET NODE_STATUS = "
				+ "(SELECT STATE_ID FROM STATE_MAHCINE WHERE STATE_DESCRIPTION = 'Active')"
				+ " where NODE_ID = '" + nodeID+"'";

		con.executeQuery(query);
		return false;
	}

}
