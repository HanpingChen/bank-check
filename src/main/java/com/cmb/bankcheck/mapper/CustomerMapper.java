package com.cmb.bankcheck.mapper;

import com.cmb.bankcheck.entity.ProcessEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-25
 * Time:13:31
 */
@Mapper
public interface CustomerMapper {

    String sql = "insert into user(age, name) values(#{age}, #{name})";
    @Insert(sql)
    int insertUser(@Param("name") String name, @Param("age") int age);

    @Select("select * from process where user_id = #{id}")
    List<ProcessEntity> queryCustomerProcesses(@Param("id") String id);

    @Select("select * from process where apply_id=#{applyId}")
    List<ProcessEntity> queryCustomerProcessesByApplyId(@Param("applyId") String applyId);

}
