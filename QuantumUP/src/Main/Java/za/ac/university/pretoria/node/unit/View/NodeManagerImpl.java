package za.ac.university.pretoria.node.unit.View;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import za.ac.university.pretoria.node.unit.Controller.DatabaseConnection;
import za.ac.university.pretoria.node.unit.Model.NodeInfo;
public class NodeManagerImpl implements NodeManager{
	
	private DatabaseConnection con;
	
	public NodeManagerImpl() {
		con=new DatabaseConnection();
	}

	@Override
	public List<NodeInfo> viewAllNodes(String nodeID) throws SQLException{
		List<NodeInfo> nodes = new ArrayList<NodeInfo>();
		
		// All the information in NODE_INFO, Active start and end times in Node Calendar, Task id and start time from TASK_TABLE
		String query = "SELECT ni.node_id, ni.node_creation_date, ni.node_status cn.node_active_start_time,"
				+ "(SELECT nt.task, nt.start_time_id FROM NODE_TASKS nt INNERJOIN NODE_INFO ni ON ni nt.tasknode_fk = ni.node_id WHERE nt.end_time IS NULL)"
				+ "FROM NODE_INFO ni INNERJOIN NODE_CALENDAR nc on nc.nodecalendar_id_fk = ni.node_id";
		
		con.executeQuery(query);
		return nodes;
		
	}

	@Override
	public boolean killNode(String nodeID) throws SQLException {
		
		String query = "UPDATE NODE_INFO "
				+ "SET NODE_STATUS = "
				+ "(SELECT STATE_ID FROM STATE_MACHINE WHERE STATE_DESCRIPTION = 'Unavailable')"
				+ " where NODE_ID == " + nodeID;
		
	
			con.executeQuery(query);
		
		return true;
	}
	@Override
	public boolean approveNode(String nodeID,LocalTime startTime,LocalTime endTime)  throws SQLException {
		
		String query="INSERT INTO NODE_INFO(node_creation_date,node_status) VALUES(Date, state_id=2) ";
		con.executeQuery(query);
		String querycalender="INSERT NODE_CALENDER( node_active_start_time, node_active_end_time) VALUES(Date,)";
		con.executeQuery(querycalender);
		return true;
	}

	@Override
	public boolean removeNode(String nodeID) throws SQLException {
		String query="DELETE FROM NODE_INFO WHERE NODE_ID == "+nodeID;
		con.executeQuery(query);
		
		return true;
	}

	@Override
	public List<NodeInfo> viewAllOwnNodes(String adminID, String nodeID) throws SQLException {
		return null;
	}

	@Override
	public boolean changeNodeToActive(String nodeID) throws SQLException {
		return false;
	}

	@Override
	public boolean changeNodeToUnavailalbe(String nodeID) throws SQLException {
		return false;
	}

}
