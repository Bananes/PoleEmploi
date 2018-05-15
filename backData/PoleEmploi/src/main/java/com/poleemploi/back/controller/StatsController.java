package com.poleemploi.back.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.poleemploi.back.bean.StatsPage;

@RestController
public class StatsController {
	
	@RequestMapping(value = "/stats/page", method = RequestMethod.POST, consumes = "application/json" , produces = "application/json")
	@ResponseBody
	public void statsByPage(@RequestBody StatsPage statsPage){
	}

}
