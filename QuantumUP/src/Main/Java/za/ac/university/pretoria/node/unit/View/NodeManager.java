package za.ac.university.pretoria.node.unit.View;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import za.ac.university.pretoria.node.unit.Model.NodeInfo;

public interface NodeManager {

	public List<NodeInfo> viewAllNodes(long nodeID) throws SQLException;
	public boolean killNode(long nodeID) throws SQLException;
	public boolean approveNode(long nodeID,LocalTime startTime,LocalTime endTime) throws SQLException;
	public boolean removeNode(long nodeID) throws SQLException;
}
