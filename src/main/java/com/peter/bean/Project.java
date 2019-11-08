package com.peter.bean;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.peter.utils.DateUtil;

public class Project {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.id
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.name
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.path
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private String path;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.create_time
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column projects.id
     *
     * @return the value of projects.id
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column projects.id
     *
     * @param id the value for projects.id
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column projects.name
     *
     * @return the value of projects.name
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column projects.name
     *
     * @param name the value for projects.name
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column projects.path
     *
     * @return the value of projects.path
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column projects.path
     *
     * @param path the value for projects.path
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column projects.create_time
     *
     * @return the value of projects.create_time
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column projects.create_time
     *
     * @param createTime the value for projects.create_time
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", path=" + path + ", createTime=" + DateFormatUtils.format(createTime, DateUtil.formatDateString) + "]";
	}
    
}