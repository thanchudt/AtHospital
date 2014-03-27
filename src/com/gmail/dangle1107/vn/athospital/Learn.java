package com.gmail.dangle1107.vn.athospital;

import java.util.Date;

public class Learn {
	private long id;
	private long user_id;
	private long knowledge_id;
	private Date last_learn_date;
	private long mark;
	private long times;
	private long total_mark;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getUserId(){
		return user_id;
	}
	
	public void setUserId(long user_id){
		this.user_id = user_id;
	}
	
	public long getKnowledgeId(){
		return knowledge_id;
	}
	
	public void setKnowledgeId(long knowledge_id){
		this.knowledge_id = knowledge_id;
	}
	
	public Date getLastLearnDate(){
		return last_learn_date;
	}
	
	public void setLastLearnDate(Date last_learn_date){
		this.last_learn_date = last_learn_date;
	}
	
	public long getMark(){
		return mark;
	}
	
	public void setMark(long mark){
		this.mark = mark;
	}
	
	public long getTimes(){
		return times;
	}
	
	public void setTimes(long times){
		this.times = times;
	}
	
	public long getTotalMark(){
		return total_mark;
	}
	
	public void setTotalMark(long total_mark){
		this.total_mark = total_mark;
	}
}
