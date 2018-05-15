package com.poleemploi.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.poleemploi.back.bean.StatsPage;
import com.poleemploi.back.service.StatsService;

@RestController
public class StatsController {
	@Autowired
	StatsService service;
	
	@RequestMapping(value = "/stats/page", method = RequestMethod.POST, consumes = "application/json" , produces = "application/json")
	@ResponseBody
	public void statsByPage(@RequestBody StatsPage statsPage){
		service.processPageStats(statsPage);
	}
	
	@RequestMapping(value = "/stats/help", method = RequestMethod.POST, consumes = "application/json" , produces = "application/json")
	@ResponseBody
	public void statsHelp(@RequestBody StatsPage statsPage){
	}

}
