package com.peter.bean;

import java.util.Date;

public class File {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column files.id
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column files.name
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column files.path
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    private String path;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column files.createTime
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column files.user_id
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    private Integer userId;
    private User user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column files.type_id
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    private Integer typeId;
    private FileType fileType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column files.id
     *
     * @return the value of files.id
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column files.id
     *
     * @param id the value for files.id
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column files.name
     *
     * @return the value of files.name
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column files.name
     *
     * @param name the value for files.name
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column files.path
     *
     * @return the value of files.path
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column files.path
     *
     * @param path the value for files.path
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column files.createTime
     *
     * @return the value of files.createTime
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column files.createTime
     *
     * @param createtime the value for files.createTime
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column files.user_id
     *
     * @return the value of files.user_id
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column files.user_id
     *
     * @param userId the value for files.user_id
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column files.type_id
     *
     * @return the value of files.type_id
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column files.type_id
     *
     * @param typeId the value for files.type_id
     *
     * @mbggenerated Sun Aug 09 11:30:22 CST 2020
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", createtime=" + createtime +
                ", userId=" + userId +
                ", user=" + user +
                ", typeId=" + typeId +
                ", fileType=" + fileType +
                '}';
    }
}