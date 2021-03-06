package com.poleemploi.back.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poleemploi.back.bean.Info;
import com.poleemploi.back.bean.StatsObject;
import com.poleemploi.back.bean.StatsPage;
import com.poleemploi.back.persistence.StatsDAO;

@Service
public class StatsService {
	private static final String TIME_ELAPSED = "Time-Elapsed";
	private static final String AMOUNT_CLICK = "Amount-Click";
	private static final String AMOUNT_ERROR = "Amount-Error";
	private static final String MOUSE_SPEED = "Mouse-Speed";
	private static final String HELP = "Help";
	private static final Integer[] maxErrorStep = {2,7,12};
	private static final Integer[] maxTimeStep = {1000,2000,3000};
	private static final Integer[] maxClickStep = {10,20,30};
	private static final Integer[] mouseSpeed = {300,500,800};
	private static final Integer[] helpList = {0,1,2};
	
	@Autowired
	private StatsDAO dao;
	@Autowired
	private SocketTransfer socketTranfer;
	/**
	 * Launch actions on page stats call
	 * @param statsPage
	 */
	public void processPageStats(StatsPage statsPage) {
		persistStats(statsPage);
		processTimeList(getListInfosFromStats(statsPage, TIME_ELAPSED),statsPage.getId());
		processClickList(getListInfosFromStats(statsPage, AMOUNT_CLICK),statsPage.getId());
		processErrorList(getListInfosFromStats(statsPage, AMOUNT_ERROR),statsPage.getId());
		processMouseSpeedList(getListInfosFromStats(statsPage,MOUSE_SPEED), statsPage.getId());
		processHelp(getListInfosFromStats(statsPage,HELP),statsPage.getId());
	}
	
	private void persistStats(StatsPage statsPage) {
		dao.save(convertStatsPageToStatsObject(statsPage));		
	}

	private void processErrorList(List<Info> listInfos,String id) {
		processInfos(listInfos,id,AMOUNT_ERROR,maxErrorStep);		
	}
	
	private void processHelp(List<Info> listInfos,String id) {
		processInfos(listInfos,id,HELP,helpList);		
	}

	private void processClickList(List<Info> listInfos,String id) {
		processInfos(listInfos,id,AMOUNT_CLICK,maxClickStep);			
	}
	private void processMouseSpeedList(List<Info> listInfos,String id) {
		processInfos(listInfos,id,MOUSE_SPEED,mouseSpeed);			
	}

	private void processTimeList(List<Info> listInfos, String id) {
		processInfos(listInfos,id,TIME_ELAPSED,maxTimeStep);				
	}
	private void processInfos(List<Info> listInfos,String id,String type,Integer[] steps){
		listInfos.forEach(info ->{
			int level;
			if(info.getValue()<=steps[0]){
				level = 0;
			}else if(info.getValue()> steps[0] && info.getValue()<= steps[1]){
				level = 1;
			}else if(info.getValue()> steps[1] && info.getValue()<= steps[2]){
				level = 2;
			}else{
				level = 3;
			}	
			
			try {
				JSONObject json = new JSONObject();
				json.put("id", id);
				json.put("type",type);
				json.put("level", level);
				socketTranfer.sendNotification(json) ;
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		});
	}


	private List<Info> getListInfosFromStats(StatsPage statsPage,String matcher) {
		return statsPage.getInfos()//
					.stream()//
					.filter(info -> info != null)
					.filter(info -> info.getName().equals(matcher))//
					.collect(Collectors.toList());		
	}
	
	private StatsObject convertStatsPageToStatsObject(StatsPage statsPage){
		StatsObject stats = new StatsObject();
		stats.setId(UUID.randomUUID());
		stats.setLocation(statsPage.getId());
		stats.setPage(statsPage.getPage());
		return stats;
		
	}
}
