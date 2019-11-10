package com.peter.bean;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

import com.peter.utils.RegexUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.peter.utils.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class User {
    private Log LOG = LogFactory.getLog(User.class);
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.id
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.name
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.password
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.real_name
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private String realName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.number
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.email
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.sex
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private Boolean sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.login_time
     *
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    private Date loginTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.id
     *
     * @return the value of users.id
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.id
     *
     * @param id the value for users.id
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.name
     *
     * @return the value of users.name
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.name
     *
     * @param name the value for users.name
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.password
     *
     * @return the value of users.password
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.password
     *
     * @param password the value for users.password
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.real_name
     *
     * @return the value of users.real_name
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.real_name
     *
     * @param realName the value for users.real_name
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.number
     *
     * @return the value of users.number
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public String getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.number
     *
     * @param number the value for users.number
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.email
     *
     * @return the value of users.email
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.email
     *
     * @param email the value for users.email
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.sex
     *
     * @return the value of users.sex
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.sex
     *
     * @param sex the value for users.sex
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.login_time
     *
     * @return the value of users.login_time
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.login_time
     *
     * @param loginTime the value for users.login_time
     * @mbggenerated Sat Oct 19 21:20:29 CST 2019
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + ", realName=" + realName + ", number="
                + number + ", email=" + email + ", sex=" + sex + ", loginTime=" + DateFormatUtils.format(loginTime,
                DateUtil.formatDateString) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                password.equals(user.password) &&
                realName.equals(user.realName) &&
                number.equals(user.number) &&
                email.equals(user.email) &&
                sex.equals(user.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, realName, number, email, sex);
    }

    public boolean check() {
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                try {
                    String value = (String) field.get(this);
                    if (StringUtils.isEmpty(value) && StringUtils.length(value) < 6){
                        LOG.info(field.getName()+" 为空或者长度小于6");
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        if (RegexUtils.isEmail(email)&&RegexUtils.isMatch("\\d+",number)) {
            return true;
        }
        else{
            LOG.info("邮箱或学号不正确");
            return false;
        }
    }

}