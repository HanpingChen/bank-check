package com.cmb.bankcheck.mapper;

import com.cmb.bankcheck.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-27
 * Time:12:42
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 通过部门查找
     * @param apart
     * @return
     */
    @Select("select id from employee where apart = #{apart}")
    public List<String> queryEmployeeByApart(@Param("apart") String apart);


    @Select("select password from employee where id = #{id}")
    public String queryPassword(@Param("id") String id);

    @Select("select * from employee")
    public List<EmployeeEntity> queryAll();

    @Select("select distinct apart from employee")
    public List<String> queryAllApart();

    @Select("select distinct position from employee")
    public List<String> queryAllPosition();

    @Select("select id from employee where branch=#{branch} and subbranch = #{subbranch} and apart=#{apart} and (position=#{position} or position='部门主管')")
    public List<String> queryHandler(@Param("branch") String branch,@Param("subbranch") String subbranch, @Param("apart") String apart, @Param("position") String position);

    @Select("select branch from employee where id=#{id}")
    String queryBranch(@Param("id") String id);

    @Select("select * from employee where id=#{id}")
    EmployeeEntity queryEmployeeInfo(String id);

}
