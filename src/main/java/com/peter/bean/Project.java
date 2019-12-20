package com.peter.bean;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Project {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.id
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.name
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.description
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.path
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    private String path;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.user_id
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    private Integer userId;
    private User user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column projects.create_time
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    private Date createTime;
    private List<Result> results;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column projects.id
     *
     * @return the value of projects.id
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
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
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
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
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
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
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column projects.description
     *
     * @return the value of projects.description
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column projects.description
     *
     * @param description the value for projects.description
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column projects.path
     *
     * @return the value of projects.path
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
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
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column projects.user_id
     *
     * @return the value of projects.user_id
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column projects.user_id
     *
     * @param userId the value for projects.user_id
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column projects.create_time
     *
     * @return the value of projects.create_time
     *
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
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
     * @mbggenerated Sat Nov 16 22:25:32 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return name.equals(project.name) &&
                description.equals(project.description) &&
                userId.equals(project.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, userId);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                ", userId=" + userId +
                ", user=" + user +
                ", createTime=" + DateFormatUtils.format(createTime,"yyyy-MM-dd") +
                '}';
    }
}