package za.ac.university.pretoria.node.mvc.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class NodeInfo {

	private String adminId;
	private String nodeId;
	private LocalDate creationDate;
	private String state;
	private LocalTime activeTime;
	private LocalTime endTime;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public LocalTime getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(LocalTime activeTime) {
		this.activeTime = activeTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

}
