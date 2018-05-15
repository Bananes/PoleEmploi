package com.poleemploi.back.bean;

public class Alert {
	private String clientId;
	private String type;
	// 0 = OK , 1 = Little confused , 2 = Confused , 3 = Really confused
	private int level;
	
	public Alert(String clientId,String type,int level){
		this.clientId = clientId;
		this.type = type;
		this.level = level;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

}
