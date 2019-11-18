package com.peter.mapper;

import com.peter.bean.Announce;
import com.peter.bean.AnnounceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnnounceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int countByExample(AnnounceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int deleteByExample(AnnounceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int insert(Announce record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int insertSelective(Announce record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    List<Announce> selectByExampleWithBLOBs(AnnounceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    List<Announce> selectByExample(AnnounceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    Announce selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int updateByExampleSelective(@Param("record") Announce record, @Param("example") AnnounceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int updateByExampleWithBLOBs(@Param("record") Announce record, @Param("example") AnnounceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int updateByExample(@Param("record") Announce record, @Param("example") AnnounceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int updateByPrimaryKeySelective(Announce record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int updateByPrimaryKeyWithBLOBs(Announce record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table announces
     *
     * @mbggenerated Sat Nov 16 15:53:31 CST 2019
     */
    int updateByPrimaryKey(Announce record);
}