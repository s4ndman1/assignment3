package za.ac.university.pretoria.node.unit.Model;

import java.util.Date;

public class NodeInfo {
	private int nodeId;
	private String nodeStatus;
	private Date nodeStartDate;
	
	
	
	public NodeInfo(int nodeId, String nodeStatus, Date nodeStartDate) {
		super();
		this.nodeId = nodeId;
		this.nodeStatus = nodeStatus;
		this.nodeStartDate = nodeStartDate;
	}
	
	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	public Date getNodeStartDate() {
		return nodeStartDate;
	}

	public void setNodeStartDate(Date nodeStartDate) {
		this.nodeStartDate = nodeStartDate;
	}

	

}
