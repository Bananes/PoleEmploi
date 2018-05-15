package com.poleemploi.back.persistence;

import com.poleemploi.back.bean.StatsObject;

public interface StatsDAO {
	
	public void persistStats(StatsObject stats);
	
	public void persistAssistance();

}
