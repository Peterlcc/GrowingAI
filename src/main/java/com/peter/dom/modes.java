package com.peter.dom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class modes {
	private String type;
	private String name;
	private List<owned> owneds;
	
	public modes(String type,String name) {
		this.type=type;
		this.name=name;
		owneds=new ArrayList<owned>();
	}
	
	public void addowend(String type,String name,String kind,String path) {
		//String[] aa =name.split("\"");
		//System.out.print(aa[1]);
		owned ow=new owned(type,name,kind,path);
		System.out.println("\nowned_add:"+type+" "+name+" "+kind+" "+path);
		owneds.add(ow);
	}
	
	public String getname() {
		return name;
	}
	public String gettype() {
		return type;
	}
	public List<owned> getowned(){
		return owneds;
	}
	public String getOwnedNamebyTyepIndex(String type,int index)
	{
		int count=0;
		for(owned ow:owneds)
		{
			if(ow.gettype().equals(type))
			{
				if(count==index)
				{
					return ow.getname();
				}
				count++;
			}
		}
		return "";
	}

}
