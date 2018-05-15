package com.poleemploi.back.service;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poleemploi.back.bean.Alert;
import com.poleemploi.back.bean.Info;
import com.poleemploi.back.bean.StatsObject;
import com.poleemploi.back.bean.StatsPage;
import com.poleemploi.back.persistence.StatsDAO;

@Service
public class StatsService {
	private static final String TIME_ELAPSED = "Time-Elapsed";
	private static final String AMOUNT_CLICK = "Amount-Click";
	private static final String AMOUNT_ERROR = "Amount-Error";
	private static final Integer[] maxErrorStep = Arrays.array(2,7,12);
	private static final Integer[] maxTimeStep = Arrays.array(1000,2000,3000);
	private static final Integer[] maxClickStep = Arrays.array(10,20,30);
	
	@Autowired
	private StatsDAO dao;
	@Autowired
	private AlertService alertService;
	/**
	 * Launch actions on page stats call
	 * @param statsPage
	 */
	public void processPageStats(StatsPage statsPage) {
		persistStats(statsPage);
		processTimeList(getListInfosFromStats(statsPage, TIME_ELAPSED),statsPage.getId());
		processClickList(getListInfosFromStats(statsPage, AMOUNT_CLICK),statsPage.getId());
		processErrorList(getListInfosFromStats(statsPage, AMOUNT_ERROR),statsPage.getId());
		
	}
	
	private void persistStats(StatsPage statsPage) {
		dao.persistStats(convertStatsPageToStatsObject(statsPage));		
	}

	private void processErrorList(List<Info> listInfos,String id) {
		processInfos(listInfos,id,"Error",maxErrorStep);		
	}

	private void processClickList(List<Info> listInfos,String id) {
		processInfos(listInfos,id,"Click",maxClickStep);			
	}

	private void processTimeList(List<Info> listInfos, String id) {
		processInfos(listInfos,id,"Time",maxTimeStep);				
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
			alertService.sendNotification(new Alert(id,type,level)) ;
		});
	}


	private List<Info> getListInfosFromStats(StatsPage statsPage,String matcher) {
		return statsPage.getInfos()//
					.stream()//
					.filter(info -> info.getName().equals(matcher))//
					.collect(Collectors.toList());		
	}
	
	private StatsObject convertStatsPageToStatsObject(StatsPage statsPage){
		// to implement
		return new StatsObject();
		
	}
}
