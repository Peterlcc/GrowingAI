package com.peter.dom;

import java.util.ArrayList;
import java.util.List;

public class Founction {
	private String source_file;
	private String name;
	private List<Param> params;
	private String ret;
	private String ret_type;
	
	public Founction(String name,String source_file) {
		this.source_file=source_file;
		this.name=name;
		params=new ArrayList<Param>();
		//System.out.println("\t\t\t\tFunction name:"+name+"\tsource_file:"+source_file+"\n");
		
	}
	public String getSource_file() {
		return source_file;
	}
	public void setSource_file(String source_file) {
		this.source_file = source_file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Param> getParams() {
		return params;
	}
	public void setParams(List<Param> params) {
		this.params = params;
	}
	public void addParam(String name,String type) {
		Param param=new Param(name,type);
		params.add(param);
	}
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getRetType() {
		return ret_type;
	}
	public void setRetType(String ret_type) {
		this.ret_type = ret_type;
	}
	

}
