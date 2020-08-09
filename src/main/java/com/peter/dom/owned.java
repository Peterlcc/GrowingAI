package com.peter.dom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class owned {
	private String type;
	private String name;
	private String kind;
	private String path;
	
	public owned(String type,String name,String kind){
		this.type=type;
		this.setname(name);
		this.setkind(kind);
	}
	public owned(String type,String name,String kind,String path){
		this.type=type;
		this.setname(name);
		this.setkind(kind);
		this.setpath(path);
	}
	
	public String gettype() {
		return type;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getkind() {
		return kind;
	}

	public void setkind(String kind) {
		this.kind = kind;
	}
	public String getpath() {
		return path;
	}
	public void setpath(String path) {
		this.path = path;
	}
	public int getClassifierNum(String path) {
		//System.out.println(path);
		String pattern = "@ownedClassifier.(\\d+)";
		 
	      // ���� Pattern ����
	      Pattern r = Pattern.compile(pattern);
	      String classifier="";
	      // ���ڴ��� matcher ����
	      Matcher m = r.matcher(path);
	      //System.out.println("find "+m.groupCount());
	      while(m.find( )) {
	         //System.out.println("Found value: " + m.group() );
	         classifier=m.group();
	      } 
	      //String[] sp=path.split("@ownedClassifier.(\\d+)");
		
		
		String[] cl=classifier.split("\\.");
		int num=Integer.parseInt(cl[1]);
		return num;
	}
	public int getownedSubcomponent_Int(String path) {
		int num=-1;
		//System.out.println(path);
		String[] sp=path.split("/@");
		String pattern = "owned(\\S+).(\\d+)";
		 
	      // ���� Pattern ����
	      Pattern r = Pattern.compile(pattern);
	      String classifier="";
	      // ���ڴ��� matcher ����
	      Matcher m = r.matcher(sp[sp.length-1]);
	      //System.out.println("*"+sp[sp.length-1]);
	      //System.out.println("find "+m.groupCount());
	      while(m.find( )) {
	         //System.out.println("Found ownedSubcomponent: " + m.group() );
	         classifier=m.group();
	      } 
	      //String[] sp=path.split("@ownedClassifier.(\\d+)");
		
		if (!classifier.equals("")) {
			String[] cl=classifier.split("\\.");
			num=Integer.parseInt(cl[1]);
		}
		return num;
	}
	public String getownedSubcomponent_String(String path) {
		int num=-1;
		//System.out.println(path);
		String[] sp=path.split("/@");
		String pattern = "owned(\\S+).(\\d+)";
		 
	      // ���� Pattern ����
	      Pattern r = Pattern.compile(pattern);
	      String classifier="";
	      // ���ڴ��� matcher ����
	      Matcher m = r.matcher(sp[sp.length-1]);
	      //System.out.println("*"+sp[sp.length-1]);
	      //System.out.println("find "+m.groupCount());
	      while(m.find( )) {
	         //System.out.println("Found ownedSubcomponent: " + m.group() );
	         classifier=m.group();
	      } 
	      //String[] sp=path.split("@ownedClassifier.(\\d+)");
		
		if (!classifier.equals("")) {
			String[] cl=classifier.split("\\.");
			return cl[0];
		}
		else {
			return "";
		}
		
	}
	public int getParam_apply_to_Port_Int(String path) {
		int num=-1;
		//System.out.println(path);
		String[] sp=path.split("/@");
		String pattern = "ownedDataPort.(\\d+)";
		 
	      // ���� Pattern ����
	      Pattern r = Pattern.compile(pattern);
	      String classifier="";
	      // ���ڴ��� matcher ����
	      Matcher m = r.matcher(sp[sp.length-1]);
	      //System.out.println("*"+sp[sp.length-1]);
	      //System.out.println("find "+m.groupCount());
	      while(m.find( )) {
	         //System.out.println("Found ownedSubcomponent: " + m.group() );
	         classifier=m.group();
	      } 
	      //String[] sp=path.split("@ownedClassifier.(\\d+)");
		
		if (!classifier.equals("")) {
			String[] cl=classifier.split("\\.");
			num=Integer.parseInt(cl[1]);
			return num;
		}
		else {
			return -1;
		}
		
	}
	public String getParam_apply_to_Port_Type(String path) {
		int num=-1;
		//System.out.println(path);
		String[] sp=path.split("/@");
		String pattern = "ownedDataPort.(\\d+)";
		 
	      // ���� Pattern ����
	      Pattern r = Pattern.compile(pattern);
	      String classifier="";
	      // ���ڴ��� matcher ����
	      Matcher m = r.matcher(sp[sp.length-1]);
	      //System.out.println("*"+sp[sp.length-1]);
	      //System.out.println("find "+m.groupCount());
	      while(m.find( )) {
	         //System.out.println("Found ownedSubcomponent: " + m.group() );
	         classifier=m.group();
	      } 
	      //String[] sp=path.split("@ownedClassifier.(\\d+)");
		
		if (!classifier.equals("")) {
			String[] cl=classifier.split("\\.");
			return cl[0];
			
		}
		else {
			return "";
		}
		
	}
	public int getRet_apply_to_Port_Int(String path) {
		int num=-1;
		//System.out.println(path);
		String[] sp=path.split("/@");
		String pattern = "ownedEventDataPort.(\\d+)";
		 
	      // ���� Pattern ����
	      Pattern r = Pattern.compile(pattern);
	      String classifier="";
	      // ���ڴ��� matcher ����
	      Matcher m = r.matcher(sp[sp.length-1]);
	      //System.out.println("*"+sp[sp.length-1]);
	      //System.out.println("find "+m.groupCount());
	      while(m.find( )) {
	         //System.out.println("Found ownedSubcomponent: " + m.group() );
	         classifier=m.group();
	      } 
	      //String[] sp=path.split("@ownedClassifier.(\\d+)");
		
		if (!classifier.equals("")) {
			String[] cl=classifier.split("\\.");
			num=Integer.parseInt(cl[1]);
			return num;
		}
		else {
			return -1;
		}
		
	}
	public String getRet_apply_to_Port_Type(String path) {
		int num=-1;
		//System.out.println(path);
		String[] sp=path.split("/@");
		String pattern = "ownedEventDataPort.(\\d+)";
		 
	      // ���� Pattern ����
	      Pattern r = Pattern.compile(pattern);
	      String classifier="";
	      // ���ڴ��� matcher ����
	      Matcher m = r.matcher(sp[sp.length-1]);
	      //System.out.println("*"+sp[sp.length-1]);
	      //System.out.println("find "+m.groupCount());
	      while(m.find( )) {
	         //System.out.println("Found ownedSubcomponent: " + m.group() );
	         classifier=m.group();
	      } 
	      //String[] sp=path.split("@ownedClassifier.(\\d+)");
		
		if (!classifier.equals("")) {
			String[] cl=classifier.split("\\.");
			return cl[0];
			
		}
		else {
			return "";
		}
		
	}
}
