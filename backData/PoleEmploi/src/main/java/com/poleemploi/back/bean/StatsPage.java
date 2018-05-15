package com.poleemploi.back.bean;
import java.util.List;

public class StatsPage {
	private String id;
	private String page;
	private List<Info> infos;
	
	public StatsPage(){
		
	}
	
	public StatsPage(String id,String page,List<Info> infos){
		this.id = id;
		this.page = page;
		this.infos = infos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public List<Info> getInfos() {
		return infos;
	}

	public void setInfos(List<Info> infos) {
		this.infos = infos;
	}

}
