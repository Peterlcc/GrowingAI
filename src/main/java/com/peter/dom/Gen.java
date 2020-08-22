package com.peter.dom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Gen {
	private String include_file;
	private String version;
	private boolean version_bind;
	private String package_name;
	private String name;
	private List<Founction> founctions;
	private String lib_path;
	private String dir_name;
	private String gen_path;
	
	public Gen(String name,String include_file,String lib_path,String dir_name,String gen_path) {
		this.include_file=include_file;
		this.name=name;
		this.setVersion("");
		founctions=new ArrayList<Founction>();
		//System.out.println("\t\t\tGen name:"+name+"\tinclude_file:"+include_file+"\n");
		this.lib_path=lib_path;
		this.dir_name=dir_name;
		this.gen_path=gen_path;
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


	public void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //�ļ�����ʱ
				InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ( (byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //�ֽ��� �ļ���С
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}
		catch (Exception e) {
			System.out.println("���Ƶ����ļ���������");
			e.printStackTrace();
		}
	}

	public void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); //����ļ��в����� �������ļ���
			File a=new File(oldPath);
			String[] file=a.list();
			File temp=null;
			for (int i = 0; i < file.length; i++) {
				if(oldPath.endsWith(File.separator)){
					temp=new File(oldPath+file[i]);
				}
				else{
					temp=new File(oldPath+File.separator+file[i]);
				}
				if(temp.isFile()){
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" +
							(temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ( (len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if(temp.isDirectory()){//��������ļ���
					copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
				}
			}
		}
		catch (Exception e) {
			System.out.println("���������ļ������ݲ�������");
			e.printStackTrace();
		}
	}

	public String readFile(String fileName)
	{
		File file = new File(fileName);
	    BufferedReader reader = null;
	    StringBuffer sbf = new StringBuffer();
	    try {
	    	//System.out.println("reading file "+file);
	        reader = new BufferedReader(new FileReader(file));
	        String tempStr;
	        while ((tempStr = reader.readLine()) != null) {
	            sbf.append(tempStr+"\n");
	        }
	        reader.close();
	        String Ret=sbf.toString();
		    Ret = Ret.replaceAll("(?!\\r)\\n", "\n");
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
	    Ret = Ret.replaceAll("(?!\\r)\\n", "\n");
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
		stringBuilder.append(func.getRetType()+" DStarPlannerROS::"+func_name+"(");
		boolean f=false;
		List<Param> params=func.getParams();
		for(Param param:params) {
			if(f) stringBuilder.append(", ");
			String pname=param.getName();
			if(pname.endsWith("_re"))
			{
				pname=pname.replace("_re","");
			}
			stringBuilder.append(param.getType()+" "+pname);
			f=true;
			//System.out.println("\t\t\t\t"+param.getType()+"\t"+param.getName());
		}
		stringBuilder.append(")");
		String context = stringBuilder.toString();
		return context;
		
	}
	
	public void gen_code() throws IOException {
		

		//String root_path = "H:\\aadl_lib";
		String root_path = "E:\\gen_test";
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
public void genCMakeLists(File f,String lib_path) throws FileNotFoundException
{
	PrintStream psOld = System.out;
	System.setOut(new PrintStream(f));
	String include_context=readFile(lib_path+File.separator+"CMakeLists.txt");
	System.out.println(include_context);
	System.setOut(psOld);
}

public void genPackageXml(File f,String lib_path) throws FileNotFoundException
{
	PrintStream psOld = System.out;
	System.setOut(new PrintStream(f));
	String include_context=readFile(lib_path+File.separator+"package.xml");
	System.out.println(include_context);
	System.setOut(psOld);
}

public void genIncludeDir(String lib_path,String gen_path)
{
	copyFolder(lib_path+File.separator+"include",gen_path+File.separator+"include");
}

public void genPluginXml(File f,String lib_path) throws FileNotFoundException {
	PrintStream psOld = System.out;
	System.setOut(new PrintStream(f));
	String include_context=readFile(lib_path+File.separator+"bgp_plugin.xml");
	System.out.println(include_context);
	System.setOut(psOld);
}

public void genSrcDir(String lib_path,String gen_path)
{
	copyFolder(lib_path+File.separator+"src",gen_path+File.separator+"src");
}

public void gen_code_different_version() throws IOException {
		String root_path = gen_path+File.separator+dir_name;
		File dirFolder = new File(root_path);
		if(!dirFolder.exists()) {
			dirFolder.mkdirs();
		}
		String gen_path = root_path;
		if(package_name != null && !package_name.equals("")) {
			gen_path = root_path + File.separator + package_name;
			File packageFolder = new File(gen_path);
			if(!packageFolder.exists()) {
				packageFolder.mkdirs();
			}
			File cmakelists = new File(gen_path +File.separator+ "CMakeLists.txt");
			cmakelists.createNewFile();
			genCMakeLists(cmakelists,lib_path);

			File packagexml = new File(gen_path +File.separator+ "package.xml");
			packagexml.createNewFile();
			genPackageXml(packagexml,lib_path);

			File pluginxml = new File(gen_path +File.separator+ "bgp_plugin.xml");
			packagexml.createNewFile();
			genPluginXml(pluginxml,lib_path);

			File include = new File(gen_path +File.separator+ "include");
			include.mkdirs();
			genIncludeDir(lib_path,gen_path);

			File src = new File(gen_path + File.separator+"src");
			src.mkdirs();
			genSrcDir(lib_path,gen_path);


			gen_path = gen_path + File.separator+"src";
		}
		
		//String file_name = gen_path+"\\"+name;
		String file_name = gen_path+File.separator+"myPlanner";
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
      
        String include_path = lib_path+File.separator+this.include_file;
        if(this.version_bind)
        	include_path = add_version_to_file(include_path, this.version);
        	
        String include_context=readFile(include_path);
		System.out.println(include_context);
		for(Founction func:founctions) {
			
			//System.out.println("\t"+func.getName());
			
			String fun_head=gen_func_head(func);
			System.out.println(fun_head);
			System.out.println("{");
			
			String file_path = lib_path+File.separator+func.getSource_file();
	        if(this.version_bind)
	        	file_path = add_version_to_file(file_path, this.version);
			
			String func_context=readFile(file_path);
			System.out.println(func_context);
			System.out.println("}");
			
		}
		System.out.println("}");
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out)),true));

				
	}



}
