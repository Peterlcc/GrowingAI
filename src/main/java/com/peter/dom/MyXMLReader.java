package com.peter.dom;

import java.io.*;  
import java.util.*;  
import org.w3c.dom.*;  
import javax.xml.parsers.*; 

public class MyXMLReader {
	public void createFile(String path) throws IOException{
        File file=new File(path);
        if(!file.exists())
            file.createNewFile();
    }
	
	public void PrintStreamDemo(String path,String output){
        try {
            FileOutputStream out=new FileOutputStream(path);
            PrintStream p=new PrintStream(out);
            
            p.println(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	
	

 
	//���Ʒ���
    public static void copy(String src, String des) throws Exception {
    	System.out.println("copy from:"+src+" to:"+des);
        //��ʼ���ļ�����
        File file1=new File(src);
        //���ļ��������ݷŽ�����
        File[] fs=file1.listFiles();
        //��ʼ���ļ�ճ��
        File file2=new File(des);
        //�ж��Ƿ�������ļ��в���û�д���
        if(!file2.exists()){
            file2.mkdirs();
        }
        
        //�����ļ����ļ���
        	for (File f : fs) {
        		if(f.isFile()){
        			//�ļ�
        			fileCopy(f.getPath(),des+"\\"+f.getName()); //�����ļ������ķ���
        		}else if(f.isDirectory()){
        			//�ļ���
        			copy(f.getPath(),des+"\\"+f.getName());//�������ø��Ʒ���      �ݹ�ĵط�,�Լ������Լ��ķ���,�Ϳ��Ը����ļ��е��ļ�����
        		}
        	}
        
        
    }

    /**
     * �ļ����Ƶľ��巽��
     */
    private static void fileCopy(String src, String des) throws Exception {
        //io���̶���ʽ
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
        int i = -1;//��¼��ȡ����
        byte[] bt = new byte[2014];//������
        while ((i = bis.read(bt))!=-1) {
            bos.write(bt, 0, i);
        }
        bis.close();
        bos.close();
        //�ر���
    }


 public static void GenAADL(String sensor,String planner) {
	 try {
	 	File f=new File("E:/2.xml"); 
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder=factory.newDocumentBuilder();   
		Document doc = builder.parse(f);   
		NodeList nl = doc.getElementsByTagName("ownedClassifier"); 
		
		//System.out.println(doc.getElementsByTagName("node").item(0));
		
		
		
		
		 FileWriter writer = new FileWriter("E:/Gtest/gen pack/"+sensor+".aadl", true);
		 
		 writer.write("\n\n");
		 
		 //�̳�global_planner
		 writer.write("thread "+planner+" extends global_planner\r\n" + 
		 		"properties\r\n" + 
		 		"	Test_Property::Test_Length=>10 m;\r\n" + 
		 		"	Test_Property::Test_Steps=>10 steps;\r\n" + 
		 		"	Test_Property::Test_Points=>10 points;\r\n" + 
		 		"end "+planner+";\n\n");
		 
		 //�̳�movebase
		 String movebase_extends="movebase_"+sensor;
		 writer.write("process movebase_"+sensor+" extends movebase\r\n" + 
		 		"features\r\n" + 
		 		"	"+sensor+"_in:in data port;\r\n" + 
		 		"end movebase_"+sensor+";\n\n");
		 
		 //movebaseʵ��
		 writer.write("process  implementation movebase_"+sensor+".impl\r\n" + 
		 		"subcomponents\r\n" + 
		 		"	ga: thread ga;\r\n" + 
		 		"end movebase_"+sensor+".impl;\n\n");
		 
		 //amclʵ��
		 writer.write("process  implementation amcl.impl\r\n" + 
		 		"end amcl.impl;\r\n\n");
		 
		 
		 //systemʵ��
		 writer.write("system implementation system1.impl\r\n" + 
		 		"subcomponents\r\n" + 
		 		"	"+movebase_extends+": process "+movebase_extends+".impl;\r\n" + 
		 		"	"+sensor+": process "+sensor+";\r\n" + 
		 		"	map_server: process map_server;\r\n" + 
		 		"	amcl:process amcl.impl;\r\n" + 
		 		"connections\r\n" + 
		 		"	system1_impl_new_connection: port "+sensor+"."+sensor+"_out -> "+movebase_extends+"."+sensor+"_in;\r\n" + 
		 		"	system1_impl_new_connection2: port map_server.map_out ->"+movebase_extends+".map_in;\r\n" + 
		 		"	system1_impl_new_connection3:port amcl.amcl_out->"+movebase_extends+".amcl_in;\r\n" + 
		 		"end system1.impl;\n\n"
		 		+ "end "+sensor+";\n\n");
		 
		 
		 
         
         writer.close();
		
		
		
		/*for (int i=0;i < l;i++){
			//System.out.println(doc.getElementsByTagName("ownedClassifier").item(i).getAttributes().getNamedItem("name").getNodeValue());
			String nodename=doc.getElementsByTagName("ownedClassifier").item(i).getAttributes().getNamedItem("name").getNodeValue();
			//System.out.println(nodename);
			
			
			
	        for (String node : nodelist) {
	            
	          if(node.equals(nodename)) {
	        	  
	        	  
	          }
	           
	       }
		}*/
 	}catch(Exception e){   
	e.printStackTrace();  
 	}
}

 
 
 public static void GenMyPack() {
	 
	 try {
		 
		 copy("E:/Gtest/node lib/my_launch","E:/Gtest/gen pack/my_launch");
			FileOutputStream out=new FileOutputStream("E:/Gtest/gen pack/my_launch/launch/amcl_demo.launch.xml");
			PrintStream amcl_demo=new PrintStream(out);
		 
		 long lasting =System.currentTimeMillis();   
		   
			File f=new File("E:/2.xml"); 
			
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();   
			DocumentBuilder builder=factory.newDocumentBuilder();   
			Document doc = builder.parse(f);   
			NodeList nl = doc.getElementsByTagName("ownedClassifier"); 
			
			//System.out.println(doc.getElementsByTagName("node").item(0));
			int l=nl.getLength();
			amcl_demo.println("<launch>\r\n");
			
			
			for (int i=0;i < l;i++){
				//System.out.println(doc.getElementsByTagName("ownedClassifier").item(i).getAttributes().getNamedItem("name").getNodeValue());
				String nodename=doc.getElementsByTagName("ownedClassifier").item(i).getAttributes().getNamedItem("name").getNodeValue();
				//System.out.println(nodename);
				
				
				File file1=new File("E:/Gtest/node lib");
		        //���ļ��������ݷŽ�����
		        File[] fs=file1.listFiles();
		        for (File nd : fs) {
		            
		          if(nd.getName().equals(nodename)) {
		        	  copy("E:/Gtest/node lib/"+nodename,"E:/Gtest/gen pack/"+nodename);
		        	  
		          }
		           
		        }
		        
		        
		        
				if(nodename.equals("amcl")) {
					//�����launch�ļ�
					fileCopy("E:/Gtest/launch lib/amcl.launch.xml","E:/Gtest/gen pack/my_launch/launch/amcl.launch.xml");
					System.out.println("amcl");
					amcl_demo.println("<!-- AMCL -->\r\n" + 
							"  <arg name=\"custom_amcl_launch_file\" default=\"$(find mylaunch)/launch/amcl.launch.xml\"/>\r\n" + 
							"  <arg name=\"initial_pose_x\" default=\"0.0\"/> <!-- Use 17.0 for willow's map in simulation -->\r\n" + 
							"  <arg name=\"initial_pose_y\" default=\"0.0\"/> <!-- Use 17.0 for willow's map in simulation -->\r\n" + 
							"  <arg name=\"initial_pose_a\" default=\"0.0\"/>\r\n" + 
							"  <include file=\"$(arg custom_amcl_launch_file)\">\r\n" + 
							"    <arg name=\"initial_pose_x\" value=\"$(arg initial_pose_x)\"/>\r\n" + 
							"    <arg name=\"initial_pose_y\" value=\"$(arg initial_pose_y)\"/>\r\n" + 
							"    <arg name=\"initial_pose_a\" value=\"$(arg initial_pose_a)\"/>\r\n" + 
							"  </include>");
					
				}
				if(nodename.equals("move_base")) {
					
					fileCopy("E:/Gtest/launch lib/move_base.launch.xml","E:/Gtest/gen pack/my_launch/launch/move_base.launch.xml");
					fileCopy("E:/Gtest/launch lib/safety_controller.launch.xml","E:/Gtest/gen pack/my_launch/launch/safety_controller.launch.xml");
					fileCopy("E:/Gtest/launch lib/velocity_smoother.launch.xml","E:/Gtest/gen pack/my_launch/launch/velocity_smoother.launch.xml");
					
					//copy("E:/Gtest/node lib/"+nodename,"E:/Gtest/gen pack/"+nodename);
					//Velocity smoother
					amcl_demo.println("<!-- Move base -->\r\n" + 
							"  <include file=\"$(find mylaunch)/launch/move_base.launch.xml\"/>");
					
					
					
					
				}
				if(nodename.equals("map_server")) {
					
					//copy("E:/Gtest/node lib/"+nodename,"E:/Gtest/gen pack/"+nodename);
					amcl_demo.println(" <!-- Map server -->\r\n" + 
							"  <arg name=\"map_file\" default=\"$(env TURTLEBOT_MAP_FILE)\"/>\r\n" + 
							"  <node name=\"map_server\" pkg=\"map_server\" type=\"map_server\" args=\"$(arg map_file)\" />");
				}
				//System.out.print("p:" + doc.getElementsByTagName("planner").item(i).getFirstChild().getNodeValue());   
				//System.out.println("t:" + doc.getElementsByTagName("topic").item(i).getFirstChild().getNodeValue());   
			
			}
			amcl_demo.println();
			amcl_demo.println("</launch>\r\n");
		 
	 }catch(Exception e){   
			e.printStackTrace();  
	 	}
 }

 

	
 public static void GenYaml() {
	 try {
		 
		 
		 fileCopy("E:/Gtest/param lib/costmap_common_params.yaml","E:/Gtest/gen pack/my_launch/param/costmap_common_params.yaml");
		 fileCopy("E:/Gtest/param lib/local_costmap_params.yaml","E:/Gtest/gen pack/my_launch/param/local_costmap_params.yaml");
		 fileCopy("E:/Gtest/param lib/global_costmap_params.yaml","E:/Gtest/gen pack/my_launch/param/global_costmap_params.yaml");
		 fileCopy("E:/Gtest/param lib/dwa_local_planner_params.yaml","E:/Gtest/gen pack/my_launch/param/dwa_local_planner_params.yaml");
		 fileCopy("E:/Gtest/param lib/move_base_params.yaml","E:/Gtest/gen pack/my_launch/param/move_base_params.yaml");
		 fileCopy("E:/Gtest/param lib/global_planner_params.yaml","E:/Gtest/gen pack/my_launch/param/global_planner_params.yaml");
		 fileCopy("E:/Gtest/param lib/navfn_global_planner_params.yaml","E:/Gtest/gen pack/my_launch/param/navfn_global_planner_params.yaml");
	 }catch(Exception e){   
		e.printStackTrace();  
	 }
 }
 
 
 
 
 
 
	public static void main(String arge[]){
		try {
			
			File mylaunch=new File("E:/mylaunch");
			if(!mylaunch.exists()){//����ļ��в�����
				mylaunch.mkdir();//�����ļ���
			}
			
			File launch=new File("E:/mylaunch/launch");
			if(!launch.exists()){//����ļ��в�����
				launch.mkdir();//�����ļ���
			}
			
			
			File param=new File("E:/mylaunch/param");
			if(!param.exists()){//����ļ��в�����
				param.mkdir();//�����ļ���
			}
			
			File xml=new File("E:/mylaunch/launch/amcl_demo.xml");
			if(!xml.exists())
				xml.createNewFile();
			File amcl=new File("E:/mylaunch/launch/amcl.xml");
			if(!amcl.exists())
				amcl.createNewFile();
		
		
		}
        catch (IOException e) {
        	e.printStackTrace();
        }
		try {
			String sensor="kinect";
			String planner="ga";
			
			fileCopy("E:/Gtest/AADL lib/"+sensor+".aadl","E:/Gtest/gen pack/"+sensor+".aadl");
			
			GenAADL(sensor,planner);
			
			GenMyPack();
			
			GenYaml();
			
			//copy("E:/Gtest/launch lib/velocity_smoother.launch.xml","E:/Gtest/gen pack/my_launch/velocity_smoother.launch.xml");
		//System.setOut(psOld);
		}catch(Exception e){   
		e.printStackTrace();  
		}
	}
}
