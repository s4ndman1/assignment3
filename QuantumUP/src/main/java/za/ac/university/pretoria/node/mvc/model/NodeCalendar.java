package za.ac.university.pretoria.node.mvc.model;

import java.util.Date;

public class NodeCalendar {
	private int nodeCalendarId;
	private Date nodeActiveStartTime;
	private Date nodeActiveEndTime;
	
	
	public int getNodeCalendarId() {
		return nodeCalendarId;
	}
	public void setNodeCalendarId(int nodeCalendarId) {
		this.nodeCalendarId = nodeCalendarId;
	}
	public Date getNodeActiveStartTime() {
		return nodeActiveStartTime;
	}
	public void setNodeActiveStartTime(Date nodeActiveStartTime) {
		this.nodeActiveStartTime = nodeActiveStartTime;
	}
	public Date getNodeActiveEndTime() {
		return nodeActiveEndTime;
	}
	public void setNodeActiveEndTime(Date nodeActiveEndTime) {
		this.nodeActiveEndTime = nodeActiveEndTime;
	}

}
