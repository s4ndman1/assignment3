package za.ac.university.pretoria.node.unit.View;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import za.ac.university.pretoria.node.unit.Model.NodeInfo;

public interface NodeManager {

	public List<NodeInfo> viewAllNodes(String nodeID) throws SQLException;
	public List<NodeInfo> viewAllOwnNodes (String adminID,String nodeID) throws SQLException;
	public boolean killNode(String nodeID) throws SQLException;
	public boolean approveNode(String adminID,LocalTime startTime,LocalTime endTime) throws SQLException;
	public boolean removeNode(String nodeID) throws SQLException;
	public boolean changeNodeToActive(String nodeID) throws SQLException;
	public boolean changeNodeToUnavailalbe(String nodeID) throws SQLException;
	
}
