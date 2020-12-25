package com.peter.dom;

public class Param {
	private String name;
	private String type;
	
	public Param(String name,String type) {
		this.name=name;
		this.type=type;
		//System.out.println("\n\t\t\t\t\tParam name:"+name+"\ttype:"+type+"\n");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
