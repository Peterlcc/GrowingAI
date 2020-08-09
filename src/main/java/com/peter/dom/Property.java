package com.peter.dom;

public class Property {
	private String kind;
	private String value;
	private String apply_to;
	
	public Property(String kind,String value,String apply_to) {
		this.kind=kind;
		this.value=value;
		this.apply_to=apply_to;
	}
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getApply_to() {
		return apply_to;
	}
	public void setApply_to(String apply_to) {
		this.apply_to = apply_to;
	}
	
	

}
