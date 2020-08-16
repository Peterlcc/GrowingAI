package com.peter.dom;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

public class XML_Reader {
	public void analise(String type,String name) {
		if (type.equals("aadl2:SystemType")){

		}

	}

	public static void xmlReader(String aaxl_path,String lib_path,String gen_path,String dir_name) {
		List<modes> modes_list=new ArrayList<>();//´æ´¢Ò»¸öxmlÀïµÄËùÓÐÄ£ÐÍ£¨classifier£©
		try {
//			File f = new File("E:/basenode.xml"); 
			File f = new File(aaxl_path);
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList nl = doc.getElementsByTagName("ownedClassifier");
			int length = nl.getLength();

			if(nl != null){
				for(int i = 0; i < length; i++){
					Node item = nl.item(i);
					String xsi_type = item.getAttributes().getNamedItem("xsi:type").getNodeValue();
					String name = item.getAttributes().getNamedItem("name").getNodeValue();
					//System.out.println("type : " + xsi_type + "  name : " + name);
					modes mode=new modes(xsi_type,name);//ÓÃÀ´´æ´¢¸÷¸öÄ£ÐÍµÄÐÅÏ¢£¬½ø³Ì¡¢Ïß³ÌÖ®ÀàµÄ
					for (Node node = item.getFirstChild(); node != null; node = node.getNextSibling()){
						String name_t="";
						String kind_t="";
						String path_t="";
						if (node.getNodeType() != Node.ELEMENT_NODE)
							continue;
						if(node.getNodeName().equals("ownedComment"))
							continue;
						//System.out.print("	--- " + node.getNodeName());
						String type_t=node.getNodeName();
						//name_t=node.getAttributes().getNamedItem("name").getNodeValue();
						//kind_t=node.getAttributes().getNamedItem("in") == null ? "  out" : "  in";
						switch(node.getNodeName()){
							//------------------------------------------------------------------------------------------
							case "ownedEventPort":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								kind_t=node.getAttributes().getNamedItem("in") == null ? "out" : "in";
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								//System.out.print(node.getAttributes().getNamedItem("in") == null ? "  out" : "  in");
								break;
							case "ownedDataPort":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								kind_t=node.getAttributes().getNamedItem("in") == null ? "out" : "in";
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								//System.out.print(node.getAttributes().getNamedItem("in") == null ? "  out" : "  in");
								break;
							case "ownedEventDataPort":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								kind_t=node.getAttributes().getNamedItem("in") == null ? "out" : "in";
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								//System.out.print(node.getAttributes().getNamedItem("in") == null ? "  out" : "  in");
								break;
							//------------------------------------------------------------------------------------------
							case "ownedSubprogramAccess":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								kind_t=node.getAttributes().getNamedItem("kind").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								//System.out.print("  "  + node.getAttributes().getNamedItem("kind"));
								break;
							case "ownedDataAccess":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								kind_t=node.getAttributes().getNamedItem("kind").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								//System.out.print("  "  + node.getAttributes().getNamedItem("kind"));
								break;
							case "ownedBusAccess":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								kind_t=node.getAttributes().getNamedItem("kind").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								//System.out.print("  "  + node.getAttributes().getNamedItem("kind"));
								break;
							case "ownedBusSubcomponent":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								break;
							case "ownedDeviceSubcomponent":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								break;
							case "ownedProcessSubcomponent":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								path_t=node.getAttributes().getNamedItem("processSubcomponentType").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								break;
							//------------------------------------------------------------------------------------------
							case "ownedAccessConnection":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								if(node.getAttributes().getNamedItem("accessCategory")!=null) {
									kind_t=node.getAttributes().getNamedItem("accessCategory").getNodeValue();
								}
								if(node.getAttributes().getNamedItem("bidirectional")!=null) {
									kind_t=node.getAttributes().getNamedItem("bidirectional").getNodeValue();
								}
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								//System.out.print("  "  + node.getAttributes().getNamedItem("accessCategory"));

								for (Node subnode = node.getFirstChild(); subnode != null; subnode = subnode.getNextSibling()) {
									if (subnode.getNodeType() != Node.ELEMENT_NODE)
										continue;
									if(subnode.getNodeName().equals("destination"));
									if(subnode.getNodeName().equals("source"));
								}

								break;
							case "ownedPortConnection":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));

								for (Node subnode = node.getFirstChild(); subnode != null; subnode = subnode.getNextSibling()) {
									if (subnode.getNodeType() != Node.ELEMENT_NODE)
										continue;
									if(subnode.getNodeName().equals("destination"));
									if(subnode.getNodeName().equals("source"));
								}

								break;
							//------------------------------------------------------------------------------------------
							case "ownedMode":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								if(node.getAttributes().getNamedItem("initial") != null)
									kind_t=node.getAttributes().getNamedItem("initial").getNodeValue();
								//System.out.print("  initial");
								break;

							case "ownedModeTransition":
								for (Node subnode = node.getFirstChild(); subnode != null; subnode = subnode.getNextSibling()) {
									if (subnode.getNodeType() != Node.ELEMENT_NODE)
										continue;
									if(subnode.getNodeName().equals("ownedTrigger"));
									//System.out.print("  " + subnode.getAttributes().getNamedItem("triggerPort"));
								}
								break;
							//------------------------------------------------------------------------------------------
							case "ownedSubprogramSubcomponent":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								break;


							case "ownedDataSubcomponent":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								break;

							case "ownedThreadSubcomponent":
								name_t=node.getAttributes().getNamedItem("name").getNodeValue();
								path_t=node.getAttributes().getNamedItem("threadSubcomponentType").getNodeValue();
								//System.out.print("  "  + node.getAttributes().getNamedItem("name"));
								break;
							//------------------------------------------------------------------------------------------
							case "ownedExtension":
								path_t=node.getAttributes().getNamedItem("extended").getNodeValue();
								break;
							case "ownedRealization":
								path_t=node.getAttributes().getNamedItem("implemented").getNodeValue();
								break;
							case "ownedPropertyAssociation"://½âÎöÊôÐÔ

								String property=node.getAttributes().getNamedItem("property").getNodeValue();

								for (Node subnode = node.getFirstChild(); subnode != null; subnode = subnode.getNextSibling()) {
									if( subnode.getNodeName().equals("appliesTo")){
										for (Node subsubnode = subnode.getFirstChild(); subsubnode != null; subsubnode = subsubnode.getNextSibling()) {

											if( subsubnode.getNodeName().equals("path")){
												//System.out.println(subsubnode.getAttributes().getNamedItem("namedElement").getNodeValue());
												path_t=subsubnode.getAttributes().getNamedItem("namedElement").getNodeValue();
												if(subsubnode.getFirstChild()!=null) {
													for (Node subsubsubnode = subsubnode.getFirstChild(); subsubsubnode != null; subsubsubnode = subsubsubnode.getNextSibling()) {

														if( subsubsubnode.getNodeName().equals("path")){
															//System.out.println(subsubsubnode.getAttributes().getNamedItem("namedElement").getNodeValue());
															path_t=path_t+subsubsubnode.getAttributes().getNamedItem("namedElement").getNodeValue();
														}
													}
												}
											}
										}
									}
									//System.out.println("------"+subnode.getNodeName());
									if( subnode.getNodeName().equals("ownedValue")){
										for (Node subsubnode = subnode.getFirstChild(); subsubnode != null; subsubnode = subsubnode.getNextSibling()) {
											//System.out.println("--------"+subsubnode.getNodeName());
											if( subsubnode.getNodeName().equals("ownedValue")){

												type_t="ownedPropertyAssociation";
												kind_t=get_property_kind(property);
												name_t=subsubnode.getAttributes().getNamedItem("value").getNodeValue();


											}
										}
									}

								}
							default:
								break;
						}
						mode.addowend(type_t, name_t, kind_t,path_t);//¸øÕâ¸öÄ£ÐÍ£¨classifier£©Ìí¼ÓÒ»¸ö×Ó×é¼þ
						//System.out.println();
					}
					modes_list.add(mode);//°ÑÕâ¸öclassi¼ÓÈëµ½ËùÓÐÄ£ÐÍlistÀï
				}
//				System.out.println(modes_list.get(11).getname());
//				List<owned> s_owneds =modes_list.get(11).getowned();
//				System.out.println(analise_owned(s_owneds,"ownedThreadSubcomponent",0));
//				for(int n=0;n<s_owneds.size();n++) {
//					owned s_owned=s_owneds.get(n);
//					//System.out.println(s_owned.gettype()+"  "+s_owned.getname()+"  "+s_owned.getkind());
//					
//				}
				List<Property> properties=new ArrayList<Property>();
				for(int m=0;m<modes_list.size();m++) {

					if(modes_list.get(m).gettype().equals("aadl2:SystemImplementation")){
						System.out.println("We Are In SystemImplementation.\n");
						List<owned> sys_owneds=new ArrayList<>();
						sys_owneds=modes_list.get(m).getowned();
						for(int n=0;n<sys_owneds.size();n++) {
							owned sys_owned=sys_owneds.get(n);


							/* *
							 * ½«global_plannerÄÚµÄº¯Êý½âÎö³öÀ´
							 * ·ÖÎª»ù½ÚµãµÄÏß³ÌÒÔ¼°¼Ì³Ð½ÚµãµÄÏß³Ì
							 * */
							if(sys_owned.gettype().equals("ownedProcessSubcomponent")) {

								gen_code();
								//
								System.out.println("\t*It Has A ProcessSubcomponent.\n");
								int cl_num=sys_owned.getClassifierNum(sys_owned.getpath());
								System.out.println("\t\tIts CLASSIFIER NUM Is:\t"+cl_num+"\tName Is:"+modes_list.get(cl_num).getname());
								System.out.println();
								//
								List<owned> proc_owneds =modes_list.get(cl_num).getowned();
								//print_owned(proc_owneds);
								/*
								 * */
								String gen_name=sys_owned.getname();
								String gen_include_file=get_properties_file(properties,gen_name,"include_file");
								String gen_version_type=get_properties_file(properties,gen_name,"version_type");
								String gen_version_bind=get_properties_file(properties,gen_name,"version_bind");
								String gen_package_name = get_properties_file(properties,gen_name,"package_name");
								boolean gen_version_bind_type = (gen_version_bind.equals("true") ? true : false);
//								System.out.println(gen_name+" " + gen_include_file + " " + gen_version_type + " " + gen_version_bind_type);
								//println(gen_version_type);
								Gen gen=new Gen(gen_name,gen_include_file,lib_path,dir_name,gen_path);
								gen.setVersion_bind(gen_version_bind_type);
								gen.setPackage_name(gen_package_name);
								if(gen_version_bind_type)
									gen.setVersion(gen_version_type);
								/*
								 * */

								for (owned s_owned : proc_owneds) {
									if(s_owned.gettype() == "ownedExtension"){
										int ex_num = sys_owned.getClassifierNum(s_owned.getpath());
										System.out.println("\t\tIts extend NUM Is:\t"+ex_num+"\tName Is:"+modes_list.get(ex_num).getname());
										System.out.println();

										List<owned> extend_owneds =modes_list.get(ex_num).getowned();
										//print_owned(extend_owneds);
										System.out.println("\t\tBase global_planner thread:");
										System.out.println();

										for (owned ex_owned : extend_owneds){
											if(ex_owned.gettype() == "ownedThreadSubcomponent"){

												int thread_impl_num = ex_owned.getClassifierNum(ex_owned.getpath());
												owned thread_owned = modes_list.get(thread_impl_num).getowned().get(0);
												int thread_type_num = ex_owned.getClassifierNum(thread_owned.getpath());
												List<owned> ports = modes_list.get(thread_type_num).getowned();
												boolean use_flag = false;
												/*
												 * */
												String thread_name=modes_list.get(thread_type_num).getname() ;
												String thread_source_file=get_properties_file(properties,thread_name,"source_file");

												Founction func=new Founction(thread_name,thread_source_file);

												/**/
												//print_owned(threads);
												//System.out.println(thread_type_num);
												//System.out.print("\t\tname:\t" + modes_list.get(thread_type_num).getname());
												//System.out.print("\ttype:\t");
												for (owned port : ports) {
													if(port.getkind().equals("out")) {
														System.out.print("\t\t\t" + port.getname());

														String founc_ret=port.getname();
														String ret_type=get_ret_type(ports,thread_name,founc_ret);
														func.setRet(founc_ret);
														func.setRetType(ret_type);
													}
												}
												System.out.print("\t" + thread_name + "(");
												//System.out.print("\tparams:");
												for (owned port : ports) {
													if(port.getkind().equals("in")){
														if(use_flag)
															System.out.print(", ");


														String param_name=port.getname();
														String param_type=get_param_type(ports,thread_name,param_name);
														System.out.print(param_type+" ");
														System.out.print(port.getname());

														func.addParam(param_name,param_type);

														use_flag = true;
													}
												}
												System.out.println(")");
												gen.addFounction(func);
											}
										}

										System.out.println();
										System.out.println("\t\tExtend global_planner thread:");
										System.out.println();
									}
									if(s_owned.gettype() == "ownedThreadSubcomponent"){
										int thread_impl_num = s_owned.getClassifierNum(s_owned.getpath());
										owned thread_owned = modes_list.get(thread_impl_num).getowned().get(0);
										int thread_type_num = s_owned.getClassifierNum(thread_owned.getpath());
										List<owned> ports = modes_list.get(thread_type_num).getowned();
										boolean use_flag = false;

										String thread_name=modes_list.get(thread_type_num).getname() ;
										String thread_source_file=get_properties_file(properties,thread_name,"source_file");
										Founction func=new Founction(thread_name,thread_source_file);

//										System.out.println(thread_name + " " + thread_source_file);
										//print_owned(threads);
										//System.out.println(thread_type_num);
										//System.out.print("\t\tname:\t" + modes_list.get(thread_type_num).getname());
										//System.out.print("\ttype:\t");
										for (owned port : ports) {
											if(port.getkind().equals("out"))
												System.out.print("\t\t\t" + port.getname());

											String founc_ret=port.getname();
											String ret_type=get_ret_type(ports,thread_name,founc_ret);
											func.setRet(founc_ret);
											func.setRetType(ret_type);
										}
										System.out.print("\t" + modes_list.get(thread_type_num).getname() + "(");
										//System.out.print("\tparams:");

										for (owned port : ports) {
											if(port.getkind().equals("in")){
												if(use_flag) {
													System.out.print(", ");
												}


												String param_name=port.getname();
												String param_type=get_param_type(ports,thread_name,param_name);
												System.out.print(param_type+" ");
												System.out.print(port.getname());

												func.addParam(param_name,param_type);

												use_flag = true;
											}
										}
										System.out.println(")");
										gen.addFounction(func);
									}
								}
								//gen.gen_code();
								gen.gen_code_different_version();
							}
							if(sys_owned.gettype().equals("ownedPropertyAssociation")) {

								gen_code();
								//ÕÒµ½ÊôÐÔÖ¸ÏòµÄclassifier
								System.out.println("\t*It Has A PropertyAssociation\n");
								int cl_num=sys_owned.getClassifierNum(sys_owned.getpath());
								System.out.println("\t\tIts CLASSIFIER NUM Is:\t"+cl_num+"\tName Is:"+modes_list.get(cl_num).getname());
								System.out.println();
								//¸ù¾ÝclassifierÕÒµ½ÊôÐÔÖ®Ö¸ÏòµÄsubcomponent
								List<owned> find_owned =modes_list.get(cl_num).getowned();
								//print_owned(find_owned);

								int sub_num=sys_owned.getownedSubcomponent_Int(sys_owned.getpath());
								String sub_name=sys_owned.getownedSubcomponent_String(sys_owned.getpath());

								System.out.println("\t\t\tIts SUBCOMPONENT NAME:\t"+find_owned(find_owned,sub_name,sub_num)+"\tSUBCOMPONENT NUM:"+sub_num);
								System.out.println();
								//
								System.out.println("\t\t\tThe\t"+sys_owned.getkind()+ "\tProperty\t"+sys_owned.getname()+"\tBelongs To:\t"+find_owned(find_owned,sub_name,sub_num));
								System.out.println();
								//
								String prop_kind=sys_owned.getkind();
								String prop_value=sys_owned.getname();
								String prop_apply_to=find_owned(find_owned,sub_name,sub_num);
								Property prop=new Property(prop_kind,prop_value,prop_apply_to);
								properties.add(prop);
								//List<owned> proc_owneds =modes_list.get(cl_num).getowned();
								//print_owned(proc_owneds);
							}
						}
					}
				}

			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void gen_code() {}

	/*
	 * Ò»¸öclassifier¾ÍÊÇÒ»¸öÄ£ÐÍ£¬process»òÕßthread
	 * owned¾ÍÊÇÕâ¸öclassifierµÄ×é¼þ£¬½ø³ÌÀïÃæµÄÏß³Ì¡¢×Ó³ÌÐòÖ®ÀàµÄ
	 * Õâ¸öº¯ÊýÔÚÒ»¸öclassifierµÄËùÓÐ×é¼þÀïÕÒÄ³Ò»ÀàÐÍµÄ×é¼þ£¬ÊäÈëÊÇÕâ¸ö×é¼þµÄÀàÐÍºÍÐòºÅ
	 * ÀýÈçÕâÖÖ#//@ownedPublicSection/@ownedClassifier.40/@ownedProcessSubcomponent.0
	 **/
	public static String find_owned(List<owned> ow,String type,int index) {
		int count=0;
		String res="";
		for(int n=0;n<ow.size();n++) {
			owned s_owned=ow.get(n);
			if(s_owned.gettype().equals(type)) {

				if(count == index) {

					res=s_owned.getname();
					break;
				}
				count++;
			}
			//System.out.println(s_owned.gettype()+"  "+s_owned.getname());

		}
		return res;
	}
	public static void print_owned(List<owned> s_owneds) {
		for(int n=0;n<s_owneds.size();n++) {
			owned s_owned=s_owneds.get(n);
			System.out.println(s_owned.gettype()+"  "+s_owned.getname()+"  "+s_owned.getkind());

		}
	}
	public static String get_property_kind(String property) {

		String pattern = "#Param_Property.(\\S+)";

		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);
		String property_kind="";
		// 现在创建 matcher 对象
		Matcher m = r.matcher(property);
		//System.out.println("*"+sp[sp.length-1]);
		//System.out.println("find "+m.groupCount());
		while(m.find( )) {
			//System.out.println("Found ownedSubcomponent: " + m.group() );
			property_kind=m.group();
		}
		//String[] sp=path.split("@ownedClassifier.(\\d+)");

		if (!property_kind.equals("")) {
			String[] cl=property_kind.split("\\.");
			return cl[1];
		}
		else {
			return "";
		}
	}

	public static String get_properties_file(List<Property> properties,String name,String type) {
		for(Property prop:properties) {
			if(prop.getApply_to().equals(name) && prop.getKind().equals(type)) {
				return prop.getValue();
			}
		}
		return "";

	}

	public static String get_param_type(List<owned> ports,String thread_name,String port_name) {
		for(owned port:ports) {
			//System.out.println("-*"+port.getname());
			if(port.getkind().equals("param_type")) {

				int apply_port_num=port.getParam_apply_to_Port_Int(port.getpath());
				//System.out.println("-*"+apply_port_num);
				String apply_port_type=port.getParam_apply_to_Port_Type(port.getpath());
				String apply_port_name=find_owned(ports,apply_port_type,apply_port_num);
				if(apply_port_name.equals(port_name)) {

					return port.getname();
				}


			}
		}
		return "";
	}

	public static String get_ret_type(List<owned> ports,String thread_name,String port_name) {
		for(owned port:ports) {
			//System.out.println("\n-*"+port.getkind()+"--"+port.getname());
			if(port.getkind().equals("return_type")) {

				int apply_port_num=port.getRet_apply_to_Port_Int(port.getpath());
				//System.out.println("-*"+apply_port_num);
				String apply_port_type=port.getRet_apply_to_Port_Type(port.getpath());
				String apply_port_name=find_owned(ports,apply_port_type,apply_port_num);
				if(apply_port_name.equals(port_name)) {

					return port.getname();
				}


			}
		}
		return "";
	}
	public static String get_version_type(List<owned> ports,String thread_name,String port_name) {
		for(owned port:ports) {
			//System.out.println("\n-*"+port.getkind()+"--"+port.getname());
			if(port.getkind().equals("version_type")) {

				int apply_port_num=port.getRet_apply_to_Port_Int(port.getpath());
				//System.out.println("-*"+apply_port_num);
				String apply_port_type=port.getRet_apply_to_Port_Type(port.getpath());
				String apply_port_name=find_owned(ports,apply_port_type,apply_port_num);
				if(apply_port_name.equals(port_name)) {

					return port.getname();
				}


			}
		}
		return "";
	}
	public static void println(String p) {
		System.out.println(p);
	}
	public static void main(String[] args)
	{
		String aaxl_path="E:\\basenode.xml";
		String lib_path="E:\\gen_test";
		String gen_path="E:\\gen_test";
		String dir_name="test";
		xmlReader(aaxl_path,lib_path,gen_path,dir_name);
	}
}
