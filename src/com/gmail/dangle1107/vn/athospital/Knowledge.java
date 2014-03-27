package com.gmail.dangle1107.vn.athospital;

public class Knowledge {
	private long id;
	private long subject_id;
	private String content;
	private long category_id;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getSubjectId(){
		return subject_id;
	}
	
	public void setSubjectId(long subject_id){
		this.subject_id = subject_id;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public long getCategoryId(){
		return category_id;
	}
	
	public void setCategoryId(long category_id){
		this.category_id = category_id;
	}	
}

