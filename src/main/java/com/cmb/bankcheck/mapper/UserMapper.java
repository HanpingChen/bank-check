package com.cmb.bankcheck.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-25
 * Time:13:31
 */
@Mapper
public interface UserMapper {

    String sql = "insert into user(age, name) values(#{age}, #{name})";
    @Insert(sql)
    int insertUser(@Param("name") String name, @Param("age") int age);

}
