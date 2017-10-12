package com.xigua.model;

public class eventNotification {
	
	private String event_class;
	private String severity;
	private Object reporting_entity;
	
	public String getEvent_class() {
		return event_class;
	}	
	public void setEvent_class(String event_class) {
		this.event_class = event_class;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public Object getReporting_entity() {
		return reporting_entity;
	}
	public void setReporting_entity(Object reporting_entity) {
		this.reporting_entity = reporting_entity;
	}
}
