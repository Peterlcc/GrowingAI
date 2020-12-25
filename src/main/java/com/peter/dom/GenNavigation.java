package com.peter.dom;

import java.io.*;
import java.util.List;

public class GenNavigation {
    private List<modes> modes_list;
    private String lib_path;
    private String gen_path;
    public GenNavigation(List<modes> modes_list,String lib_path,String gen_path)
    {
        this.modes_list=modes_list;
        this.lib_path=lib_path;
        this.gen_path=gen_path;
    }
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //ɾ����������������
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //ɾ�����ļ���
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//��ɾ���ļ���������ļ�
                delFolder(path + "/" + tempList[i]);//��ɾ�����ļ���
                flag = true;
            }
        }
        return flag;
    }
    public void copyFolder(String oldPath, String newPath) throws IOException {

        //System.out.println(oldPath);
        (new File(newPath)).mkdirs();
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

    public boolean isExtend(modes mode,String extend)
    {
            List<owned> owneds=mode.getowned();
            for(owned ow:owneds)
            {
                if(ow.gettype().equals("ownedExtension")) {
                    if(modes_list.get(ow.getClassifierNum(ow.getpath())).getname().equals("global_planner.impl"))
                    {
                        return true;
                    }
                }
            }
            return false;
    }
    public String getClassifierNameByOw(owned ow)
    {
        return modes_list.get(ow.getClassifierNum(ow.getpath())).getname();
    }
    public int getTestTime(modes mode,String env)
    {
        List<owned> owneds=mode.getowned();
        for(owned ow:owneds)
        {
            if(ow.gettype().equals("ownedPropertyAssociation")) {
                if(ow.getkind().equals("test_result_time"))
                {
                    String apply_type=ow.getownedSubcomponent_String(ow.getpath());
                    int apply_num=ow.getownedSubcomponent_Int(ow.getpath());
                    String env_name=mode.getOwnedNamebyTyepIndex(apply_type,apply_num);
                    if(env_name.equals(env))
                    {
                        return Integer.parseInt(ow.getname());
                    }

                }
            }
        }
        return -1;
    }
    public int getTestLength(modes mode,String env)
    {
        List<owned> owneds=mode.getowned();
        for(owned ow:owneds)
        {
            if(ow.gettype().equals("ownedPropertyAssociation")) {
                if(ow.getkind().equals("test_result_length"))
                {
                    String apply_type=ow.getownedSubcomponent_String(ow.getpath());
                    int apply_num=ow.getownedSubcomponent_Int(ow.getpath());
                    String env_name=mode.getOwnedNamebyTyepIndex(apply_type,apply_num);
                    if(env_name.equals(env))
                    {
                        return Integer.parseInt(ow.getname());
                    }

                }
            }
        }
        return -1;
    }
    public String getPluginName(modes mode)
    {
        List<owned> owneds=mode.getowned();
        for(owned ow:owneds)
        {
            if(ow.gettype().equals("ownedPropertyAssociation")) {
                if(ow.getkind().equals("plugin_name"))
                {
                    return ow.getname();

                }
            }
        }
        return "";
    }
    public void genClassifier(int index) throws IOException {
        //ownedPropertyAssociation move_base package_file #//@ownedPublicSection/@ownedClassifier.28/@ownedDataSubcomponent.0
        //type name kind path
        modes mode=modes_list.get(index);
        List<owned> owneds=mode.getowned();
        System.out.println("gen "+index+" "+mode.getname());
        for(owned ow:owneds)
        {
            if(ow.gettype().equals("ownedPropertyAssociation")) {
                if(ow.getkind().equals("package_file"))
                {
                    String source=lib_path+File.separator+ow.getname();
                    String dest=gen_path+File.separator+ow.getname();
                    copyFolder(source,dest);

                }
            }

        }
    }
    public void genGlobalPlannerLaunch(String plugin_name) throws FileNotFoundException {
        File file = new File(gen_path+File.separator+"fake_move_base_amcl.launch");
        if(!file.exists()){
            try {
                //System.out.println("Creat File ");
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(gen_path+File.separator+"fake_move_base_amcl.launch")),true));
        System.out.println("<launch>\n" +
                "\n" +
                "  <node pkg=\"move_base\" type=\"move_base\" respawn=\"false\" name=\"move_base\" output=\"screen\" clear_params=\"true\">\n" +
                "    <rosparam file=\"$(find rbx1_nav)/config/fake/costmap_common_params.yaml\" command=\"load\" ns=\"global_costmap\" />\n" +
                "    <rosparam file=\"$(find rbx1_nav)/config/fake/costmap_common_params.yaml\" command=\"load\" ns=\"local_costmap\" />\n" +
                "    <rosparam file=\"$(find rbx1_nav)/config/fake/local_costmap_params.yaml\" command=\"load\" />\n" +
                "    <rosparam file=\"$(find rbx1_nav)/config/fake/global_costmap_params.yaml\" command=\"load\" />\n" +
                "    <rosparam file=\"$(find rbx1_nav)/config/fake/base_local_planner_params.yaml\" command=\"load\" />\n" +
                "    <param name=\"base_global_planner\" value=\""+plugin_name+"\"/>\n" +

                "  </node>\n" +
                "  \n" +
                "</launch>");
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out)),true));
    }
    public void genGlobalPlanner(List<modes> modes_list,String env,String require) throws IOException {
        int time_min=1000;
        int length_min=1000;
        int ret_index=-1;
        String plugin_name="";
        for(int m=0;m<modes_list.size();m++)
        {
            modes mode=modes_list.get(m);
            List<owned> owneds=mode.getowned();
            System.out.println(m+" "+mode.getname());
            if(isExtend(mode,"global_planner.impl"))
            {
                int time=getTestTime(mode,env);
                int length=getTestLength(mode,env);
                System.out.println(env+"\\test_time:"+time);
                System.out.println(env+"\\test_length:"+length);
                if(require.equals("time"))
                {
                    if(time<time_min)
                    {
                        ret_index=m;
                        time_min=time;
                        plugin_name=getPluginName(mode);
                    }
                }
                else if(require.equals("length"))
                {

                    if(length<length_min)
                    {
                        ret_index=m;
                        length_min=length;
                        plugin_name=getPluginName(mode);
                        //System.out.println(length+" "+length_min+" "+m);
                    }
                }
            }
        }
        int classifier_num=ret_index;
        if(classifier_num>=0)
        {
            System.out.println(classifier_num);

            genClassifier(classifier_num);
            genGlobalPlannerLaunch(plugin_name);
        }

        //gen global_planner base package
        String source=lib_path+File.separator+"global_planner";
        String dest=gen_path+File.separator+"global_planner";
        copyFolder(source,dest);

    }
    public void genSystem(String env,String require) throws IOException {
        for(int m=0;m<modes_list.size();m++)
        {
            modes mode=modes_list.get(m);
            List<owned> owneds=mode.getowned();
            if(mode.getname().equals("navigation.impl"))
            {
                for(owned ow:owneds)
                {
                    if(ow.gettype().equals("ownedProcessSubcomponent"))
                    {
                        if(getClassifierNameByOw(ow).equals("global_planner.impl"))
                        {
                            genGlobalPlanner(modes_list,env,require);
                        }
                        else{
                            genClassifier(ow.getClassifierNum(ow.getpath()));
                        }
                    }
                }
            }

        }
    }

    public void gen(String env,String require) throws IOException {
//        String env="map2";
        //String require="time";
//        String require="length";
        delFolder(gen_path);
        genSystem(env,require);
    }

}
