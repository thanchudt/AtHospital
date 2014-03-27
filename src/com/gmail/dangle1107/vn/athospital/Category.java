package com.gmail.dangle1107.vn.athospital;

public class Category {
	private long id;
	private String name;
	private long parent_id;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public long getParentId(){
		return parent_id;
	}
	
	public void setParentId(long parent_id){
		this.parent_id = parent_id;
	}
	
	// Will be used by the ArrayAdapter in the ListView
		@Override
		public String toString() {
			return name;
		}
}
