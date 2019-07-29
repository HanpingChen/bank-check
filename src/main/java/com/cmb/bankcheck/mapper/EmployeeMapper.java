package com.cmb.bankcheck.mapper;

import com.cmb.bankcheck.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;

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
}
