package com.cmb.bankcheck.mapper;

import com.cmb.bankcheck.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    public List<EmployeeEntity> queryEmployeeByApart(String apart);


    @Select("select password from employee where id = #{id}")
    public String queryPassword(@Param("id") String id);
}
