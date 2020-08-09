package com.peter.dom;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Gen {
	private String include_file;
	private String version;
	private boolean version_bind;
	private String package_name;
	private String name;
	private List<Founction> founctions;
	
	public Gen(String name,String include_file) {
		this.include_file=include_file;
		this.name=name;
		this.setVersion("");
		founctions=new ArrayList<Founction>();
		//System.out.println("\t\t\tGen name:"+name+"\tinclude_file:"+include_file+"\n");
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public boolean getVersion_bind() {
		return version_bind;
	}
	
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public void setVersion_bind(boolean version_bind) {
		this.version_bind = version_bind;
	}
	
	public String getInclude_file() {
		return include_file;
	}
	public void setInclude_file(String include_file) {
		this.include_file = include_file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Founction> getFounctions() {
		return founctions;
	}
	public void setFounctions(List<Founction> founctions) {
		this.founctions = founctions;
	}
	public void addFounction(Founction founc) {
		this.founctions.add(founc);
	}
	public String readFile(String fileName) {
		File file = new File(fileName);
	    BufferedReader reader = null;
	    StringBuffer sbf = new StringBuffer();
	    try {
	    	//System.out.println("reading file "+file);
	        reader = new BufferedReader(new FileReader(file));
	        String tempStr;
	        while ((tempStr = reader.readLine()) != null) {
	            sbf.append(tempStr+"\r\n");
	        }
	        reader.close();
	        String Ret=sbf.toString();
		    Ret = Ret.replaceAll("(?!\\r)\\n", "\r\n");
		    return Ret;
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    String Ret=sbf.toString();
	    Ret = Ret.replaceAll("(?!\\r)\\n", "\r\n");
	    return Ret;
	}
	public String gen_func_head(Founction func) {
		StringBuilder stringBuilder = new StringBuilder();
		//System.out.println("\t\t"+func.getSource_file()+"==>"+func.getName());
		String func_name=func.getName();
		String[] sp=func_name.split("_");
		//System.out.println(sp[sp.length-1]);
		if(sp[sp.length-1].equals("re")) {
			func_name=func_name.replace("_re", "");
		}
		stringBuilder.append(func.getRetType()+" "+func_name+"(");
		boolean f=false;
		List<Param> params=func.getParams();
		for(Param param:params) {
			if(f) stringBuilder.append(", ");
			stringBuilder.append(param.getType()+" "+param.getName());
			f=true;
			//System.out.println("\t\t\t\t"+param.getType()+"\t"+param.getName());
		}
		stringBuilder.append(")");
		String context = stringBuilder.toString();
		return context;
		
	}
	
	public void gen_code() throws IOException {

		String root_path = "H:\\aadl_lib";
        File file = new File(root_path+"\\"+name+".cpp");
        //����ļ������ڣ����Զ������ļ���
        if(!file.exists()){
            try {
            	//System.out.println("Creat File ");
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(root_path+"\\"+name+".cpp")),true));
        String include_context=readFile(root_path+"\\"+this.include_file);
		System.out.println(include_context);
		for(Founction func:founctions) {
			
			//System.out.println("\t"+func.getName());
			
			String fun_head=gen_func_head(func);
			System.out.println(fun_head);
			System.out.println("{");
			String func_context=readFile(root_path+"\\"+func.getSource_file());
			System.out.println(func_context);
			System.out.println("}");
			
		}
		System.out.println("}");
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out)),true));

				
	}
public String add_version_to_file(String s,String version){
	//System.out.println(s+"_"+version);
	String[] sp=s.split("\\.");
    return sp[0]+"_"+version+"."+sp[1];
}
public void gen_code_different_version() throws IOException {
		String root_path = "E:\\gen_test";
		String gen_path = root_path;
		if(package_name != null && !package_name.equals("")) {
			gen_path = root_path + "\\" + package_name;
			File packageFolder = new File(gen_path);
			if(!packageFolder.exists()) {
				packageFolder.mkdirs();
			}
			File cmakelists = new File(gen_path + "\\CMakeLists.txt");
			cmakelists.createNewFile();
			File packagexml = new File(gen_path + "\\package.xml");
			packagexml.createNewFile();
			File include = new File(gen_path + "\\include");
			include.mkdirs();
			File src = new File(gen_path + "\\src");
			src.mkdirs();
			gen_path = gen_path + "\\src";
		}
		
		String file_name = gen_path+"\\"+name;
		if(this.version_bind)
			file_name += "_"+this.version+".cpp";
		else
			file_name += ".cpp";
        File file = new File(file_name);
        //����ļ������ڣ����Զ������ļ���
        if(!file.exists()){
            try {
            	//System.out.println("Creat File ");
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(file_name)),true));
      
        String include_path = root_path+"\\"+this.include_file;
        if(this.version_bind)
        	include_path = add_version_to_file(include_path, this.version);
        	
        String include_context=readFile(include_path);
		System.out.println(include_context);
		for(Founction func:founctions) {
			
			//System.out.println("\t"+func.getName());
			
			String fun_head=gen_func_head(func);
			System.out.println(fun_head);
			System.out.println("{");
			
			String file_path = root_path+"\\"+func.getSource_file();
	        if(this.version_bind)
	        	file_path = add_version_to_file(file_path, this.version);
			
			String func_context=readFile(file_path);
			System.out.println(func_context);
			System.out.println("}");
			
		}
		
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out)),true));

				
	}



}
