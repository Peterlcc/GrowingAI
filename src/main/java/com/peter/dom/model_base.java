package com.peter.dom;

import java.io.*;  
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.*;  
import javax.xml.parsers.*; 

public class model_base {
	
	public static boolean is_func(String line) {
		String pattern = "void ([\\s\\S]*) ";
		 
	    // ���� Pattern ����
	    Pattern r = Pattern.compile(pattern);
	    String classifier="";
	    // ���ڴ��� matcher ����
	    Matcher m = r.matcher(line);
	    //System.out.println("*"+sp[sp.length-1]);
	    //System.out.println("find "+m.groupCount());
	    while(m.find( )) {
	       //System.out.println("Found ownedSubcomponent: " + m.group() );
	       classifier=m.group();
	       //print(classifier);
	       return true;
	    }
	    return false;
	      
	}
	
	public static String match_params(String line) {
		String pattern = "\\(([\\s\\S]*)\\)";
		 
	    // ���� Pattern ����
	    Pattern r = Pattern.compile(pattern);
	    String classifier="";
	    // ���ڴ��� matcher ����
	    Matcher m = r.matcher(line);
	    //System.out.println("*"+sp[sp.length-1]);
	    //System.out.println("find "+m.groupCount());
	    while(m.find( )) {
	       //System.out.println("Found ownedSubcomponent: " + m.group() );
	       classifier=m.group();
	       print("params->"+classifier+"\n");
	       classifier=classifier.replace("(", "");
	       classifier=classifier.replace(")", "");
	       return classifier;
	    }
	    return "";
	      
	}
	
	public static List<Param> get_params(String line){
		List<Param> params=new ArrayList<Param>();
		Param param=new Param("name","std::string");
		params.add(param);
		Param param2=new Param("costmap_ros","costmap_2d::Costmap2DROS*");
		params.add(param2);
		
		return params;
	}
	public static void gen_param(Param p) {
		print("\t\t"+p.getName()+":"+" in data port"+";");
	}
	public static void gen_return(Founction func) {
		print("\t\t"+func.getRet()+":"+" out event data port"+";");;
	}
	
	public static void gen_thread_features(Founction func) {
		print("\tfeatures");
		for(Param p:func.getParams()) {
			gen_param(p);
		}
		gen_return(func);
	}
	
	public static void gen_thread_properties(Founction func) {
		print("\tproperties");
		for(Param p:func.getParams()) {
			print("\t\t"+"Param_Property::param_type=>"+"\""+p.getType()+"\""+" applies to "+p.getName()+";");
		}
		print("\t\t"+"Param_Property::return_type=>"+"\""+func.getRetType()+"\""+" applies to "+func.getRet()+";");
	}
	
	public static void gen_thread(Founction func) {
		print("thread "+func.getName());
		gen_thread_features(func);
		gen_thread_properties(func);
		print("end "+func.getName()+";");
	}
	
	public static void gen_subprogram_features(Founction func) {
		print("\tfeatures");
		for(Param p:func.getParams()) {
			print("\t\t"+p.getName()+":"+" in parameter "+"Param_Type::"+p.getType()+";");
		}
		print("\t\t"+func.getRet()+":"+" out parameter "+"Param_Type::"+func.getRetType()+";");
	}
	
	public static void gen_subprogram_properties(Founction func) {
		print("\tproperties");
		
			print("\t\t"+"Source_Language=>"+"(C++)");
			print("\t\t"+"Source_Name=>"+"\""+func.getName()+"\"");
			print("\t\t"+"Source_Text=>"+"\""+"dstar_global_planner.cpp"+"\"");
			
	}
	
	public static void gen_subprogram(Founction func) {
		print("subprogram "+func.getName());
		gen_subprogram_features(func);
		gen_subprogram_properties(func);
		print("end "+func.getName()+";");
	}
	
	public static void gen_aadl_model(Founction func) {
		gen_thread(func);
		gen_subprogram(func);
	}
	
	
	
	
	public static void process_line(String line) {
		
		
		
		if (is_func(line)){
			print("function->"+line+"\n");
			String func_name="initialize";
			String source_file="dstar_global_planner.cpp";
			Founction func=new Founction(func_name,source_file);
			func.setRet("void");
			func.setRetType("void");
			List<Param> params=new ArrayList<Param>();
			String param_string=match_params(line);
			//print(param_string);
			params=get_params(param_string);
			func.setParams(params);
			
			gen_aadl_model(func);
			
		}
	}
	private static boolean find_for(String line) {
		String pattern = "for\\s*\\(([\\s\\S]*)\\)\\s*";
		 
	    // ���� Pattern ����
	    Pattern r = Pattern.compile(pattern);
	    String classifier="";
	    // ���ڴ��� matcher ����
	    Matcher m = r.matcher(line);
	    //System.out.println("*"+sp[sp.length-1]);
	    //System.out.println("find "+m.groupCount());
	    while(m.find( )) {
	       //System.out.println("Found ownedSubcomponent: " + m.group() );
	       classifier=m.group();
	    
	       return true;
	    }
	    return false;
	}
	private static boolean find_right_brace(String line) {
		String pattern = "([\\s\\S]*)\\}";
		 
	    // ���� Pattern ����
	    Pattern r = Pattern.compile(pattern);
	    String classifier="";
	    // ���ڴ��� matcher ����
	    Matcher m = r.matcher(line);
	    //System.out.println("*"+sp[sp.length-1]);
	    //System.out.println("find "+m.groupCount());
	    while(m.find( )) {
	       //System.out.println("Found ownedSubcomponent: " + m.group() );
	       classifier=m.group();
	    
	      return true;
	      
	    }
	    return false;
	}
	private static boolean find_assignment(String line) {
		String pattern = "=";
		 
	    // ���� Pattern ����
	    Pattern r = Pattern.compile(pattern);
	    String classifier="";
	    // ���ڴ��� matcher ����
	    Matcher m = r.matcher(line);
	    //System.out.println("*"+sp[sp.length-1]);
	    //System.out.println("find "+m.groupCount());
	    while(m.find( )) {
	       //System.out.println("Found ownedSubcomponent: " + m.group() );
	       classifier=m.group();
	       return true;
	    }
	    return false;
	}
	private static boolean flag=false;
	private static void line_type(String line) {
		
		if(find_for(line)) {
			flag=true;
			print("find for.");
		}
		if(find_assignment(line)) print("find assignment.");
		if(find_right_brace(line)) {
			flag=false;
			print("for end");
		}
	}
	
	public static void levenshtein(String str1,String str2) {
        //���������ַ����ĳ��ȡ�
        int len1 = str1.length();
        int len2 = str2.length();
        //��������˵�����飬���ַ����ȴ�һ���ռ�
        int[][] dif = new int[len1 + 1][len2 + 1];
        //����ֵ������B��
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //���������ַ��Ƿ�һ�����������ϵ�ֵ
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //ȡ����ֵ����С��
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        //System.out.println("��\""+str1+"\"��\""+str2+"\"�ıȽ�");
        //ȡ�������½ǵ�ֵ��ͬ����ͬλ�ô���ͬ�ַ����ıȽ�
        System.out.println("���첽�裺"+dif[len1][len2]);
        //�������ƶ�
        float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        System.out.println("���ƶȣ�"+similarity+"\n");
    }

    //�õ���Сֵ
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }
    
    private static List<String> get_for_blocks(File f) throws IOException{
    	InputStreamReader reader;
		
		reader = new InputStreamReader(new FileInputStream(f));
		BufferedReader br = new BufferedReader(reader);
		String line = br.readLine();
		List<String> for_blocks=new ArrayList<String>();
		int c=0;
		String for_block="";
	    while(line != null) {
	        //System.out.println(c++);
	        //print(line);
	        //process_line(line);
	    	for_block="";
	        if(find_for(line)) {
	        	for_block+=line+"\n";
	        	line = br.readLine();
	        	while(line != null) {
	        		if(find_right_brace(line)) {
	        			for_block+=line+"\n";
	        			for_blocks.add(for_block);
	        			break;
	        		}
	        		for_block+=line+"\n";
	        		line = br.readLine();
	        	}
	        	print(for_block);
	        }
	    	line = br.readLine();
	    }
	    
	    return for_blocks;
    }
	
	
	private static void print(Object s) {
		System.out.println(s);
	}
	private static void read_functions() throws IOException {
		List<Founction> founcs=new ArrayList<Founction>();
		
		File f = new File("H:\\model_base\\dstar\\src\\dstar_global_planner.cpp");
		File f2 = new File("H:\\model_base\\relaxed_astar\\src\\RAstar_ros.cpp");
		
		List<String> for_blocks1=new ArrayList<String>();
		List<String> for_blocks2=new ArrayList<String>();
		for_blocks1=get_for_blocks(f);
		for_blocks2=get_for_blocks(f2);
		print(for_blocks1.size());
		print(for_blocks2.size());
		for (int i=0;i<for_blocks1.size();i++) {
			for (int j=0;j<for_blocks2.size();j++) {
				System.out.println("��\""+i+"\"��\""+j+"\"�ıȽ�");
				levenshtein(for_blocks1.get(i),for_blocks2.get(j));
			}
		}
		int c=0;
		String for_block="";
	    
		
	    
	}
	public static void main(String[] args) throws IOException {
		read_functions();
	}
}
