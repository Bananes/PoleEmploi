package com.poleemploi.back.bean;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="stats")
public class StatsObject{
	@Id
	private UUID id;
	private String location;
	private String page;

}
