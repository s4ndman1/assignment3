package za.ac.university.pretoria.node.api;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

public interface NodeUI {

	public List<NodeInfo> viewAllNodes() throws SQLException;
	public List<NodeInfo> viewAllOwnNodes (String adminID) throws SQLException;
	public boolean killNode(String nodeID) throws SQLException;
	public String approveNode(String adminID,LocalTime startTime,LocalTime endTime) throws SQLException, NodeException;
	public boolean removeNode(String nodeID) throws SQLException;
	public boolean changeNodeToActive(String nodeID) throws SQLException;
	
}
