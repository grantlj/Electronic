package com.ts.bean;

import java.util.Date;

public class TempData {
  private Integer id;
  private Date date;
  private Double temperature;
  private int runTime;
  
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public Double getTemperature() {
	return temperature;
}
public void setTemperature(Double temperature) {
	this.temperature = temperature;
}
public int getRunTime() {
	return runTime;
}
public void setRunTime(int runTime) {
	this.runTime = runTime;
}
  
}
